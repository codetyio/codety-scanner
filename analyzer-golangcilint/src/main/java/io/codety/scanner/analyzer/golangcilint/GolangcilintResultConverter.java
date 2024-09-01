package io.codety.scanner.analyzer.golangcilint;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintIssue;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintPos;
import io.codety.scanner.analyzer.golangcilint.dto.GolangcilintRoot;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.CodetyConsoleLogger;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class GolangcilintResultConverter {

    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput, String absolutePath) {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();
        String prefixPath = absolutePath.endsWith("/") ? absolutePath : absolutePath + "/";

        try {
            GolangcilintRoot golangcilintRoot = JsonFactoryUtil.objectMapper.readValue(errorOutput, GolangcilintRoot.class);

            ArrayList<GolangcilintIssue> issues = golangcilintRoot.getIssues();
            for(GolangcilintIssue golangIssue : issues){

                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();

                String ruleId = golangIssue.getFromLinter();
                String description = golangIssue.getText().replaceAll("\\n", " ");

                GolangcilintPos pos = golangIssue.getPos();
                int line = pos.getLine();
                String filename = pos.getFilename();
                issueDto.setIssueCategory("generic");
                issueDto.setIssueCode(ruleId);
                issueDto.setDescription(description);
                issueDto.setStartLineNumber(line);
                issueDto.setPriority(3);//
                issueDto.setFilePath(prefixPath + filename);

                codeAnalysisIssueDtos.add(issueDto);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        return codeAnalysisIssueDtos;
    }
}
