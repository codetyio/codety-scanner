package io.codety.scanner.analyzer.checkov;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.checkov.dto.*;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class CheckovResultConverter {
    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();
        CheckovRoot[] checkovResultArray = new CheckovRoot[1];
        try {

            if(errorOutput.trim().startsWith("{")){
                CheckovRoot checkovRoot = JsonFactoryUtil.objectMapper.readValue(errorOutput, CheckovRoot.class);
                checkovResultArray[0] = checkovRoot;
            }else {
                checkovResultArray = JsonFactoryUtil.objectMapper.readValue(errorOutput, checkovResultArray.getClass());
            }

            for(CheckovRoot checkovRoot : checkovResultArray){

                CheckovResults results = checkovRoot.getResults();
                if(results == null){
                    continue;
                }

                ArrayList<CheckovFailedCheck> failedChecks = results.getFailed_checks();
                for(CheckovFailedCheck failedCheck : failedChecks){
                    String externalRuleId = failedCheck.getCheck_id();
                    String shortDescription = failedCheck.getCheck_name();
                    ArrayList<Integer> fileLineRange = failedCheck.getFile_line_range();
                    CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                    if(fileLineRange!=null && fileLineRange.size() > 0) {
                        if(fileLineRange.size()>=1){
                            issueDto.setStartLineNumber(fileLineRange.get(0));
                        }
                        if(fileLineRange.size()>=2){
                            issueDto.setStartLineNumber(fileLineRange.get(1));
                        }
                    }
                    issueDto.setIssueCode(externalRuleId);
                    issueDto.setIssueCategory("security");
                    issueDto.setDescription(shortDescription);
                    issueDto.setPriority(4);
                    String filePath = failedCheck.getFile_path();
                    if(filePath.startsWith("/")){
                        filePath = filePath.substring(1);
                    }
                    issueDto.setFilePath(filePath);
                    String resource = failedCheck.getResource();
                    if(resource!=null && !resource.isEmpty()) {
                        issueDto.setPackagePath(resource);
                    }

                    codeAnalysisIssueDtos.add(issueDto);
                }


            }



        } catch (Exception e) {
            CodetyConsoleLogger.debug("Failed to deserialize checkov json result");
        }

        return codeAnalysisIssueDtos;
    }
}
