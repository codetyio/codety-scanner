package io.codety.scanner.analyzer.pylint;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.dto.AnalyzerConfigurationDetailDto;
import io.codety.scanner.analyzer.pylint.dto.PylintIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.reporter.dto.CodeAnalysisResultDto;
import io.codety.scanner.util.JsonFactoryUtil;


public class PylintResultsConverter {

    public static CodeAnalysisResultDto convertFormat(String output, AnalyzerConfigurationDetailDto runnerConfiguration) throws JsonProcessingException {

        CodeAnalysisResultDto resultDto = new CodeAnalysisResultDto(runnerConfiguration.getLanguage(), runnerConfiguration.getCodeAnalyzerType());

        PylintIssueDto[] issueDtoList = new PylintIssueDto[1];
        if(output.startsWith("{")){
            PylintIssueDto pylintIssueDto = JsonFactoryUtil.objectMapper.readValue(output, PylintIssueDto.class);
            issueDtoList[0] = pylintIssueDto;
        }else{
            issueDtoList = JsonFactoryUtil.objectMapper.readValue(output, new PylintIssueDto[0].getClass());
        }

        for(PylintIssueDto pylintIssueDto : issueDtoList){
            CodeAnalysisIssueDto issue = new CodeAnalysisIssueDto();
            issue.setStartLineNumber(pylintIssueDto.getLine());
            issue.setIssueCode(pylintIssueDto.getSymbol());
            issue.setIssueCategory(pylintIssueDto.getType());
            issue.setPriority(3);

            String message = pylintIssueDto.getMessage();
            int maxLimit = 100;
            if(message!=null && message.length() > maxLimit){
                message = message.substring(0, maxLimit) + "...";
            }
            issue.setDescription(message);

            issue.setFilePath(pylintIssueDto.getPath());
            issue.setEndLineNumber(pylintIssueDto.getEndLine());
            issue.setPackagePath(pylintIssueDto.getModule());
            resultDto.addIssue(issue);

        }


        return resultDto;
    }
}
