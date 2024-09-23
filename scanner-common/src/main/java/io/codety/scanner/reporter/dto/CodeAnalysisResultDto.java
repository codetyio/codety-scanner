package io.codety.scanner.reporter.dto;

import io.codety.common.dto.CodeAnalyzerType;
import io.codety.scanner.util.StringUtil;

import java.util.*;

public class CodeAnalysisResultDto {
    private String displayTitle;
    private String language; // only be used for analysis result distribution,e.g. review comment title.
    private CodeAnalyzerType codeAnalyzerType;
    private Map<String, List<CodeAnalysisIssueDto>> issuesByFile = new LinkedHashMap<>();

    public CodeAnalysisResultDto(String language, CodeAnalyzerType codeAnalyzerType) {
        this.language = language;
        this.codeAnalyzerType = codeAnalyzerType;
        if(language!=null) {
            this.displayTitle = StringUtil.toCamelCaseWord(language);
        }
    }

    public void addIssue(CodeAnalysisIssueDto issueDto) {
        if(issueDto == null){
            return;
        }
        String filePath = issueDto.getFilePath();
        issuesByFile.putIfAbsent(filePath, new ArrayList<>());
        issuesByFile.get(filePath).add(issueDto);
    }

    public void addIssues(List<CodeAnalysisIssueDto> codeAnalysisIssueDtoList) {
        if(codeAnalysisIssueDtoList==null){
            return;
        }

        for(CodeAnalysisIssueDto issueDto : codeAnalysisIssueDtoList){
            addIssue(issueDto);
        }

    }

    ///-----------------------------------------------

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public CodeAnalyzerType getCodeAnalyzerType() {
        return codeAnalyzerType;
    }

    public void setCodeAnalyzerType(CodeAnalyzerType codeAnalyzerType) {
        this.codeAnalyzerType = codeAnalyzerType;
    }

    public Map<String, List<CodeAnalysisIssueDto>> getIssuesByFile() {
        return issuesByFile;
    }
    public void setIssuesByFile(Map<String, List<CodeAnalysisIssueDto>> issuesByFile) {
        this.issuesByFile = issuesByFile;
    }

}
