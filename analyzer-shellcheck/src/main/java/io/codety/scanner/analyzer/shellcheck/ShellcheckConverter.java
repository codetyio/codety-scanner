package io.codety.scanner.analyzer.shellcheck;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.shellcheck.dto.ShellcheckIssue;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class ShellcheckConverter {
    public static List<CodeAnalysisIssueDto> convertResult(String successOutput, String localGitRepoPath) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();
        try {
            ShellcheckIssue[] shellcheckIssue = JsonFactoryUtil.objectMapper.readValue(successOutput, new ShellcheckIssue[0].getClass());

            if(shellcheckIssue == null){
                return codeAnalysisIssueDtos;
            }
            for(ShellcheckIssue issue : shellcheckIssue){
                String file = issue.getFile();
                if(file.startsWith(localGitRepoPath)){
                    file = file.substring(localGitRepoPath.length()+1);
                }
                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                issueDto.setFilePath(file);
                issueDto.setPriority(getPriority(issue.getLevel()));
                issueDto.setStartLineNumber(issue.getLine());
                issueDto.setEndLineNumber(issue.getEndLine());
                issueDto.setDescription(issue.getMessage());
                issueDto.setIssueCategory("styling");//

                codeAnalysisIssueDtos.add(issueDto);
            }

        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.debug("Failed to convert the shellcheck result", e);
        }


        return codeAnalysisIssueDtos;
    }

    private static Integer getPriority(String level) {
        int result = 3;
        if("error".equalsIgnoreCase(level)){
            result = 4;
        }

        return result;
    }
}
