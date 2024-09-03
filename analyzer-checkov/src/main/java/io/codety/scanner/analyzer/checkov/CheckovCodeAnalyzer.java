package io.codety.scanner.analyzer.checkov;

import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckovCodeAnalyzer implements CodeAnalyzerInterface {
    private static final String checkov = "checkov";
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();

        String[] command;

        //checkov --skip-resources-without-violations --skip-results-upload  --quiet -o json  -d . > checkov.json
        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            command = new String[]{checkov, "--skip-resources-without-violations", "--skip-results-upload", "--quiet", "-o", "json", "-d",  request.getLocalGitRepoPath()};
        }else{
            //--check CKV_GCP_33,CKV_GCP_34,CKV_GCP_35 ...
            command = new String[]{checkov, "--check",  runnerConfiguration.getPayload(), "--skip-resources-without-violations", "--skip-results-upload", "--quiet", "-o", "json", "-d",  request.getLocalGitRepoPath()};
        }
        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = CheckovResultConverter.convertResult(successOutput);
            if(codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()){
                return list;
            }
            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            resultDto.setDisplayTitle("IaC");
            resultDto.addIssues(codeAnalysisIssueDtoList);
            list.add(resultDto);

        } catch (Exception e) {
            CodetyConsoleLogger.info("Skip checkov analyzer due to exceptions");
            CodetyConsoleLogger.debug("Skip checkov analyzer due to exceptions " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.iac, CodeAnalyzerType.checkov), request);
    }
}
