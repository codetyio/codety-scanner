package io.codety.scanner.analyzer.trivy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codety.scanner.analyzer.trivy.dto.TrivyResultDto;
import io.codety.scanner.analyzer.trivy.dto.TrivyResultListDto;
import io.codety.scanner.analyzer.trivy.dto.TrivySecretDto;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrivyResultConverter {

    private static final int defaultPriority = 2;
    private static final String secretIssueCategory = "hard-coded secrets";
    private static final Map<String, Integer> priorityMapping = Map.of(
            "LOW", 1,
            "UNKNOWN", 2,
            "MEDIUM", 3,
            "HIGH", 4,
            "CRITICAL", 5
    );
    public static List<CodeAnalysisIssueDto> convertResult(String trivyPayload) {
        List<CodeAnalysisIssueDto> result = new ArrayList<>();
        ObjectMapper objectMapper = JsonFactoryUtil.objectMapper;
        try {
            TrivyResultListDto trivyResultListDto = objectMapper.readValue(trivyPayload, TrivyResultListDto.class);
            ArrayList<TrivyResultDto> trivyResultListDtoResults = trivyResultListDto.getResults();
            if(trivyResultListDtoResults == null){
                return result;
            }
            for(TrivyResultDto resultDto : trivyResultListDtoResults){
                String filePath = resultDto.getTarget();
                ArrayList<TrivySecretDto> secrets = resultDto.getSecrets();
                if(secrets!=null) {
                    for (TrivySecretDto secretDto : secrets) {
                        CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();
                        issueDto.setFilePath(filePath);
                        issueDto.setPriority(getPriority(secretDto)); //UNKNOWN  LOW:  , MEDIUM:  , HIGH:  , CRITICAL

                        issueDto.setIssueCategory(secretIssueCategory);
                        issueDto.setIssueCode(secretDto.getRuleID());
                        issueDto.setDescription("Storing sensitive data in repos can lead to significant security issues");
                        issueDto.setStartLineNumber(secretDto.getStartLine());
                        issueDto.setEndLineNumber(secretDto.getEndLine());
                        issueDto.setCweId(522);//Insufficiently Protected Credentials https://cwe.mitre.org/data/definitions/522.html
                        result.add(issueDto);
                    }
                }

            }


        } catch (JsonProcessingException e) {
            CodetyConsoleLogger.info("Failed to deserialize trivy result " + e.getMessage());
        }

        return result;
    }

    private static Integer getPriority(TrivySecretDto secretDto) {
        Integer priority = priorityMapping.get(secretDto.getSeverity());
        if(priority == null){
            priority = defaultPriority;
        }
        return priority;
    }
}
