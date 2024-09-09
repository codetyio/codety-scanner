package io.codety.scanner.analyzer.shellcheck;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class ShellcheckCodeAnalyzer implements CodeAnalyzerInterface {
    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        ArrayList<CodeAnalysisResultDto> codeAnalysisResultDtos = new ArrayList<>();

        String[] command;

        String localGitRepoPath = request.getLocalGitRepoPath();
        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
//            command = new String[]{"shellcheck", "--format=json", localGitRepoPath + "/**/*.sh"};
            //find ~/git/codety-scanner/code-issue-examples -type f \( -name '*.sh' -o -name '*.bash' -o -name '*.ksh' -o -name '*.bashrc' -o -name '*.bash_profile' -o -name '*.bash_login' -o -name '*.bash_logout' \) | xargs shellcheck --format=json
            command = new String[]{"find", localGitRepoPath, "-type", "f", "\\( -name '*.sh' -o -name '*.bash' -o -name '*.ksh' -o -name '*.bashrc' -o -name '*.bash_profile' -o -name '*.bash_login' -o -name '*.bash_logout' \\)", "|", "xargs", "shellcheck", "--format=json"};

        }else{
            command = new String[]{"shellcheck", "--format=json", localGitRepoPath + "/**/*.sh"};
        }
        try {
            RuntimeExecUtil.RuntimeExecResult runtimeExecResult = RuntimeExecUtil.exec(command, "/", 60, false, null);

            CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());
            codeAnalysisResultDtos.add(resultDto);

            String errorOutput = runtimeExecResult.getErrorOutput();
            String successOutput = runtimeExecResult.getSuccessOutput();
            if(errorOutput!=null && errorOutput.length() > 0){
                CodetyConsoleLogger.debug("Warnings from shellcheck: " + errorOutput);
            }
            if(successOutput!=null && successOutput.length() > 0) {
                List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList = ShellcheckConverter.convertResult(successOutput, localGitRepoPath);
                resultDto.addIssues(codeAnalysisIssueDtoList);
            }
        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed to run shellcheck analyzer ", e);
            CodetyConsoleLogger.info("Failed to run shellcheck analyzer " + e.getMessage());
        }

        return codeAnalysisResultDtos;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.shell, CodeAnalyzerType.shellcheck), request);
    }
}