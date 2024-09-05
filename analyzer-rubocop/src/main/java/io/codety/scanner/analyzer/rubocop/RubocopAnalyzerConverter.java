package io.codety.scanner.analyzer.rubocop;

import io.codety.scanner.analyzer.rubocop.dto.RubocopFile;
import io.codety.scanner.analyzer.rubocop.dto.RubocopIssueLocation;
import io.codety.scanner.analyzer.rubocop.dto.RubocopOffense;
import io.codety.scanner.analyzer.rubocop.dto.RubocopRoot;
import io.codety.scanner.reporter.dto.CodeAnalysisIssueDto;
import io.codety.scanner.util.JsonFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class RubocopAnalyzerConverter {
    public static List<CodeAnalysisIssueDto> convertResult(String errorOutput, String basePath) throws Exception {
        ArrayList<CodeAnalysisIssueDto> codeAnalysisIssueDtos = new ArrayList<>();

        RubocopRoot rubocopRoot = JsonFactoryUtil.objectMapper.readValue(errorOutput, RubocopRoot.class);
        ArrayList<RubocopFile> files = rubocopRoot.getFiles();
        if(files == null){
            return codeAnalysisIssueDtos;
        }

        for(RubocopFile rubocopFile : files){

            ArrayList<RubocopOffense> rubocopIssues = rubocopFile.getOffenses();
            if(rubocopIssues == null || rubocopIssues.isEmpty()){
                continue;
            }

            String path = rubocopFile.getPath();
            if(path.startsWith(basePath)){
                path = path.substring(basePath.length()+1);
            }


            for(RubocopOffense rubocopOffense : rubocopIssues){
                CodeAnalysisIssueDto issueDto = new CodeAnalysisIssueDto();

                String externalRuleId = rubocopOffense.getCop_name();
                String[] split = externalRuleId.split("/");
                String category = split[0].toLowerCase();
                RubocopIssueLocation location = rubocopOffense.getLocation();
                issueDto.setStartLineNumber(location.getStart_line());
                issueDto.setEndLineNumber(location.getLast_line());
                issueDto.setIssueCode(externalRuleId);
                issueDto.setIssueCategory(category);
                issueDto.setDescription(rubocopOffense.getMessage());
                issueDto.setPriority(convertPriority(rubocopOffense.getSeverity()));
                issueDto.setFilePath(path);
                codeAnalysisIssueDtos.add(issueDto);
            }
        }


        return codeAnalysisIssueDtos;
    }

    private static Integer convertPriority(String severity) {
        Integer priority = 3;
        if(severity.equalsIgnoreCase("info")){
            priority = 1;
        }else if(severity.equalsIgnoreCase("refactor")){
            priority = 2;
        }else if(severity.equalsIgnoreCase("convention")){
            priority = 2;
        }else if(severity.equalsIgnoreCase("warning")){
            priority = 3;
        }else if(severity.equalsIgnoreCase("error")){
            priority = 4;
        }else if(severity.equalsIgnoreCase("fatal")){
            priority = 5;
        }
        return priority;
    }
}
