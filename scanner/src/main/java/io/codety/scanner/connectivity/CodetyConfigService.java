package io.codety.scanner.connectivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDto;
import io.codety.scanner.analyzer.dto.materialized.CodetyConfigPullApiRequestDto;
import io.codety.scanner.analyzer.dto.materialized.CodetyRulesetPullApiResponseProtocolDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

@Service
public class CodetyConfigService {

    private static String defaultHost = "https://app.codety.io";
    private static File tmpConfigDownloadFolder = new File("/tmp/codety_tmp_files" + "/" + UUID.randomUUID());
    private static int defaultTimeout = 10;
    private static String invalidTimeoutSetting = "Invalid timeout setting.";
    private static String apiEndpoint = "api/ruleset/pull";

    public AnalyzerConfigurationDto downloadRulesetConfig(AnalyzerRequest analyzerRequest) {
        AnalyzerConfigurationDto analyzerConfigurationDto = new AnalyzerConfigurationDto();
        String codetyToken = analyzerRequest.getCodetyToken();
        if (codetyToken == null) {
            CodetyConsoleLogger.info(CodetyConstant.INFO_CODETY_TOKEN_NOT_FOUND);
            return analyzerConfigurationDto;
        }

        String codetyHost = analyzerRequest.getCodetyHost();
        String codetyTimeout = analyzerRequest.getCodetyTimeout();
        int timeout = defaultTimeout;
        if (codetyTimeout != null) {
            try {
                timeout = Integer.valueOf(timeout);
            } catch (Exception e) {
                CodetyConsoleLogger.debug(invalidTimeoutSetting);
            }
        }

        String uri = apiEndpoint;
        String host = codetyHost != null ? codetyHost : defaultHost;
        host = host.endsWith("/") ? host.substring(0, host.length()-1) : host;//remove ending '/' from host.
        String url = host + uri;

        String requestPayload = null;
        try {
            CodetyConfigPullApiRequestDto value = new CodetyConfigPullApiRequestDto(codetyToken, analyzerRequest.getGitRepoFullName(), analyzerRequest.getGitBaseHttpsUrl());
            requestPayload = JsonFactoryUtil.objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.info("Failed to prepare request data before  custom ruleset.");
        }
        CodetyConsoleLogger.debug("Retrieved Codety settings payload: " + requestPayload);
        try {

            String body = HttpRequestUtil.post(url, requestPayload, timeout);

            CodetyRulesetPullApiResponseProtocolDto resultProtocolDto = JsonFactoryUtil.objectMapper.readValue(body, CodetyRulesetPullApiResponseProtocolDto.class);

            analyzerConfigurationDto = AnalyzerConfigurationDtoConverter.convertExternalCodetyApiProcotol(resultProtocolDto);
            List<AnalyzerConfigurationDetailDto> configurationDetailDtoList = analyzerConfigurationDto.getConfigurationDetailDtoList();
            if (configurationDetailDtoList != null) {
                for (AnalyzerConfigurationDetailDto detailDto : configurationDetailDtoList) {

                    if (!tmpConfigDownloadFolder.exists()) {
                        boolean createdDirs = tmpConfigDownloadFolder.mkdirs();
                        if (!createdDirs) {
                            throw new RuntimeException("Failed to create folder " + tmpConfigDownloadFolder + " for writing configs. ");
                        }
                    }

                    String payloadStr = detailDto.getPayload();

                    if (payloadStr != null) {
                        File configFile = new File(tmpConfigDownloadFolder.getAbsoluteFile() + "/" + UUID.randomUUID());
                        configFile.createNewFile();
                        FileWriter fileWriter = new FileWriter(configFile);
                        fileWriter.write(payloadStr);
                        fileWriter.flush();
                        fileWriter.close();
                        detailDto.setFile(configFile);
                    }
                }
            }

            if(analyzerConfigurationDto.getSettingDto()!=null){
                analyzerRequest.setCodetyAccountType(analyzerConfigurationDto.getSettingDto().getAccountType());
            }
        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed retrieving settings from " + url, e);
            CodetyConsoleLogger.info("Failed loading configs from Codety server");
        }


        return analyzerConfigurationDto;
    }


    public void cleanupTmpDirs() {

        if (tmpConfigDownloadFolder != null && tmpConfigDownloadFolder.isDirectory()) {
            for (File f : tmpConfigDownloadFolder.listFiles()) {
                try {
                    f.delete();
                } catch (Exception e) {
                    CodetyConsoleLogger.debug("Failed to clean up file " + f.getAbsoluteFile());
                }
            }
        }
    }
}
