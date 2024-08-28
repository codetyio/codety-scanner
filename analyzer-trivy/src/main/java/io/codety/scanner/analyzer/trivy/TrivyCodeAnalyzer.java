package io.codety.scanner.analyzer.trivy;

import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrivyCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        ArrayList<CodeAnalysisResultDto> list = new ArrayList();

        String[] command = new String[]{"trivy", "filesystem", "--format", "json",   request.getLocalGitRepoPath()};

        try {

            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, "/", 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();
//            CodetyConsoleLogger.info("errorOutput: " + errorOutput);
//            CodetyConsoleLogger.info("successOutput: " + successOutput);

            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = TrivyResultConverter.convertResult(successOutput);
            if (codeAnalysisIssueDtoList == null || codeAnalysisIssueDtoList.isEmpty()) {
                return list;
            }

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());

            resultDto.setDisplayTitle("Security Analysis");
            resultDto.addIssues(codeAnalysisIssueDtoList);
            list.add(resultDto);

        } catch (Exception e) {
            String cmdarray = "Failed to analyze source code via Trivy";
            CodetyConsoleLogger.debug(cmdarray, e);
            CodetyConsoleLogger.info(cmdarray);
        }

        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return new ArrayList<>();
    }
}
