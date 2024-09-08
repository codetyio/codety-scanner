package io.codety.scanner.analyzer.stylelint;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.stylelint.dto.StylelintIssue;
import io.codety.scanner.analyzer.stylelint.dto.StylelintIssueWarning;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class StylelintConverter {
    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput, String localGitRepoPath) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();
        try {
            StylelintIssue[] stylelintIssues = JsonFactoryUtil.objectMapper.readValue(errorOutput, new StylelintIssue[0].getClass());
            if(stylelintIssues==null){
                return codeAnalysisIssueDtos;
            }

            for(StylelintIssue stylelintIssue : stylelintIssues){
                List<StylelintIssueWarning> warnings = stylelintIssue.getWarnings();
                if(warnings == null){
                    continue;
                }
                String source = stylelintIssue.getSource();
                if(source.startsWith(localGitRepoPath)){
                    source = source.substring(localGitRepoPath.length()+1);
                }
                for(StylelintIssueWarning stylelintIssueWarning : warnings){
                    CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                    issueDto.setStartLineNumber(stylelintIssueWarning.getLine());
                    issueDto.setEndLineNumber(stylelintIssueWarning.getEndLine());
                    issueDto.setDescription(stylelintIssueWarning.getText());
                    issueDto.setIssueCode(stylelintIssueWarning.getRule());
                    issueDto.setIssueCategory("styling");
                    issueDto.setPriority(getPriority(stylelintIssueWarning.getSeverity()));
                    issueDto.setFilePath(source);
                    codeAnalysisIssueDtos.add(issueDto);
                }

            }

        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.debug("Failed to parse the stylelint result ", e);
        }


        return codeAnalysisIssueDtos;
    }

    private static final String error = "error";
    private static Integer getPriority(String severity) {

        int result = 3;

        if(error.equalsIgnoreCase(severity)){
            result = 4;
        }

        return result;
    }
}
