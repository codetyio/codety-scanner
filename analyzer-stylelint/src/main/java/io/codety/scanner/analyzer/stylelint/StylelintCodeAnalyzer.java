package io.codety.scanner.analyzer.stylelint;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.common.dto.LanguageType;
import io.codety.scanner.analyzer.CodeAnalyzerInterface;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.service.dto.AnalyzerRequest;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.RuntimeExecUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class StylelintCodeAnalyzer implements CodeAnalyzerInterface {

    @Value("${codety.stylelint.path}")
    String stylelintPath;
    @Value("${codety.base.path}")
    String basePath;

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {
        CodetyConsoleLogger.info("Scanning "+runnerConfiguration.getLanguage()+" code via "+runnerConfiguration.getCodeAnalyzerType().name()+"...");
        File file = runnerConfiguration.getFile();
//cppcheck  . --suppressions-list=suppression.txt  --xml 2>error1.txt
        ArrayList<CodeAnalysisResultDto> list = new ArrayList();
        String localGitRepoPath = request.getLocalGitRepoPath();
        String analyzePath = localGitRepoPath + "/**/*.css";

        String[] command;
        try {
            String configPath = basePath + (basePath.endsWith("/")?"":basePath+"/") + "stylelint.js";
            if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
                command = new String[]{stylelintPath + "/node_modules/.bin/stylelint", "--quiet", "--formatter", "json", "--config", configPath, analyzePath};
            }else{
                command = new String[]{stylelintPath + "/node_modules/.bin/stylelint", "--quiet", "--formatter", "json", "--config", configPath, analyzePath};
            }
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, null, 60, false, null);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();

            List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = StylelintConverter.convertResult(errorOutput, localGitRepoPath);
            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            resultDto.addIssues(codeAnalysisIssueDtoList);
            list.add(resultDto);

        } catch (Exception e) {

            CodetyConsoleLogger.info("Failed to scan code via Stylelint " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.css, CodeAnalyzerType.stylelint), request);
    }
}
