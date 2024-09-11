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

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ShellcheckCodeAnalyzer implements CodeAnalyzerInterface {

    String[] suffixList = new String[]{".sh", ".bash",".ksh", ".bashrc", ".bash_profile", ".bash_login", ".bash_logout"};

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerConfigurationDetailDto runnerConfiguration, AnalyzerRequest request) {

        ArrayList<CodeAnalysisResultDto> codeAnalysisResultDtos = new ArrayList<>();

        String[] command;

        String localGitRepoPath = request.getLocalGitRepoPath();

        List<File> findAllShellScripts = findShellScripts(localGitRepoPath, suffixList);
        List<String> cmdList = new ArrayList<>();
        if(runnerConfiguration.getPayload() == null || runnerConfiguration.getPayload().isEmpty()){
            cmdList.add("shellcheck");
            cmdList.add("--format=json");
            for(File f : findAllShellScripts){
                cmdList.add(f.getAbsolutePath());
            }

            command = cmdList.toArray(new String[0]);

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

    private List<File> findShellScripts(String localGitRepoPath, String[] suffixList) {
        File f = new File(localGitRepoPath);
        List<File> result = new ArrayList<>();
        Queue<File> fileQueue = new LinkedList<>();
        fileQueue.add(f);
        while(!fileQueue.isEmpty()){
            int size = fileQueue.size();
            for(int i=0; i<size; i++){
                File poll = fileQueue.poll();

                for(File child : poll.listFiles()){
                    if(child.isDirectory()){
                        fileQueue.add(child);
                    }else if (child.isFile() && isShellScript(child, suffixList)){
                        result.add(child);
                    }
                }
            }

        }
        return result;
    }

    private boolean isShellScript(File poll, String[] suffixList) {
        if(poll == null){
            return false;
        }
        String name = poll.getName().toLowerCase();
        for(String s : suffixList){
            if(name.endsWith(s)){
                return true;
            }
        }

        return false;
    }

    @Override
    public List<CodeAnalysisResultDto> analyzeCode(AnalyzerRequest request) {
        return analyzeCode(new AnalyzerConfigurationDetailDto(LanguageType.shell, CodeAnalyzerType.shellcheck), request);
    }
}
