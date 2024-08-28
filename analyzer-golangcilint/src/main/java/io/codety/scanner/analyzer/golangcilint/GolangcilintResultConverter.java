package io.codety.scanner.analyzer.golangcilint;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintIssue;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintPos;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintRoot;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class GolangcilintResultConverter {

    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();

        try {
            GolangcilintRoot golangcilintRoot = JsonFactoryUtil.objectMapper.readValue(errorOutput, GolangcilintRoot.class);

            ArrayList<GolangcilintIssue> issues = golangcilintRoot.getIssues();
            for(GolangcilintIssue golangIssue : issues){

                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();

                String ruleId = golangIssue.getFromLinter();
                String description = golangIssue.getText();

                GolangcilintPos pos = golangIssue.getPos();
                int line = pos.getLine();
                String filename = pos.getFilename();

                issueDto.setIssueCode(ruleId);
                issueDto.setDescription(description);
                issueDto.setStartLineNumber(line);
                issueDto.setPriority(3);//
                issueDto.setFilePath(filename);

                codeAnalysisIssueDtos.add(issueDto);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return codeAnalysisIssueDtos;
    }
}
