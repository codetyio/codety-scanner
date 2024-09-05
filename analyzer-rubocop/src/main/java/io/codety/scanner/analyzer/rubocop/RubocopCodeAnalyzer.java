package io.codety.scanner.analyzer.rubocop;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class RubocopCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        ArrayList<CodeAnalysisResultDto> codeAnalysisResultDtos = new ArrayList<>();

        File file = runnerConfiguration.getFile();
//cppcheck  . --suppressions-list=suppression.txt  --xml 2>error1.txt

        String[] command;

        String localGitRepoPath = request.getLocalGitRepoPath();
        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            command = new String[]{"rubocop", "--format", "json", localGitRepoPath};

        }else{
            String enabledRules = runnerConfiguration.getPayload();
            command = new String[]{"rubocop", "--format", "json", "--only", enabledRules, localGitRepoPath};
        }
        try {

            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);



            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            codeAnalysisResultDtos.add(resultDto);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();
            if(errorOutput!=null && errorOutput.length() > 0){
                CodetyConsoleLogger.debug("Warnings from rubucop: " + errorOutput);
            }
            if(successOutput!=null && successOutput.length() > 0) {
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = RubocopAnalyzerConverter.convertResult(successOutput, localGitRepoPath);
                resultDto.addIssues(codeAnalysisIssueDtoList);
            }
        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed to run Rubocop analyzer ", e);
            CodetyConsoleLogger.info("Failed to run Rubocop analyzer " + e.getMessage());
        }

        return codeAnalysisResultDtos;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.ruby, CodeAnalyzerType.rubocop), request);
    }
}
