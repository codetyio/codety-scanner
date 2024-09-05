package io.codety.scanner.analyzer.eslint;

import io.codety.scanner.analyzer.eslint.dto.EslintErrorMessage;
import io.codety.scanner.analyzer.eslint.dto.EslintResult;
import io.codety.scanner.analyzer.eslint.dto.EslintResults;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;

public class EslintResultsConverter {

    public static void convertFormat(CodeAnalysisResultDto resultDto, EslintResults eslintResults, String sourceLocation) {


        if(eslintResults == null || eslintResults.getResults() == null){
            return;
        }

        for(EslintResult eslintResult : eslintResults.getResults()){
            EslintErrorMessage[] messages = eslintResult.getMessages();
            for(EslintErrorMessage message : messages){

                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                String replace = eslintResult.getFilePath().replace(sourceLocation, "");//get relative path.
                if(replace.charAt(0) == '/'){
                    replace = replace.substring(1);
                }
                issueDto.setFilePath(replace);
                String ruleId = message.getRuleId();
                if(ruleId!=null && ruleId.startsWith(EslintConfigFileFactory.CURRENT_RULE)){
                    ruleId = ruleId.substring(EslintConfigFileFactory.CURRENT_RULE.length()+1);
                }
                if(ruleId!=null && ruleId.contains("/")){
                    ruleId = ruleId.substring(ruleId.indexOf('/')+1);
                }
                issueDto.setIssueCode(ruleId);
                issueDto.setPriority(message.getSeverity());
                issueDto.setDescription(message.getMessage());
                issueDto.setStartLineNumber(message.getLine());
                String nodeType = message.getNodeType();
                if(nodeType == null){
                    issueDto.setIssueCategory("generic");
                }else{
                    issueDto.setIssueCategory(nodeType);
                }
                if(issueDto.getPriority() == null){
                    issueDto.setPriority(2);
                }

                resultDto.addIssue(issueDto);
            }
        }

    }
}
