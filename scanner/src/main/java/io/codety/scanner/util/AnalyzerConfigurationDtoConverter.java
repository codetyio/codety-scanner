package io.codety.scanner.util;

import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDto;
import io.codety.scanner.analyzer.dto.materialized.CodetyConfigPullApiSettingDto;
import io.codety.scanner.analyzer.dto.materialized.CodetyRulesetPullApiPayloadDto;
import io.codety.scanner.analyzer.dto.materialized.CodetyRulesetPullApiResponseProtocolDto;
import io.codety.common.dto.CodeAnalyzerType;

import java.util.Base64;

public class AnalyzerConfigurationDtoConverter {

    private static final String unexpectedCodeAnalyzerTypeCode_prefix = "Received unexpected codeAnalyzerType ";
    private static final String unexpectedCodeAnalyzerTypeCode_suffix = "  from Codety Analyzer, you may need to use the later version of the Codety analyzer. ";

    public static AnalyzerConfigurationDto convertExternalCodetyApiProcotol(CodetyRulesetPullApiResponseProtocolDto resultProtocolDto) {
        AnalyzerConfigurationDto result = new AnalyzerConfigurationDto();
        if (resultProtocolDto == null) {
            return result;
        }

        CodetyConfigPullApiSettingDto basicSetting = resultProtocolDto.getSetting();
        result.setSettingDto(basicSetting);
        if (resultProtocolDto.getPayloads() != null) {
            for (CodetyRulesetPullApiPayloadDto codetyApiPayloadDto : resultProtocolDto.getPayloads()) {
                AnalyzerConfigurationDetailDto analyzerConfigurationDetailDto = new AnalyzerConfigurationDetailDto();
                analyzerConfigurationDetailDto.setLanguage(codetyApiPayloadDto.getLanguage());
                String payload = codetyApiPayloadDto.getPayload();
                if(codetyApiPayloadDto.isEncoded() && payload!=null && payload.length() > 0){
                    payload = new String(Base64.getDecoder().decode(payload));
                }
                analyzerConfigurationDetailDto.setPayload(payload);
                analyzerConfigurationDetailDto.setPluginCode(codetyApiPayloadDto.getPluginCode());

                Integer ruleRunnerTypeFromCodetyApi = codetyApiPayloadDto.getCodeAnalyzerType();

                CodeAnalyzerType codeAnalyzerType = CodeAnalyzerType.valueOfCode(ruleRunnerTypeFromCodetyApi);
                if(codeAnalyzerType == null){
                    CodetyConsoleLogger.debug(unexpectedCodeAnalyzerTypeCode_prefix +ruleRunnerTypeFromCodetyApi+ unexpectedCodeAnalyzerTypeCode_suffix);
                }
                analyzerConfigurationDetailDto.setCodeAnalyzerType(codeAnalyzerType);

                result.getConfigurationDetailDtoList().add(analyzerConfigurationDetailDto);
            }

        }


        return result;
    }
}
