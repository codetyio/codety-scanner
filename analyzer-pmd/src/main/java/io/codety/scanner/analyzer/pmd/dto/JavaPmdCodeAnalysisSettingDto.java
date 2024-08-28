package io.codety.scanner.analyzer.pmd.dto;

import io.codety.scanner.util.CodeSourceDirectoryType;

import java.util.ArrayList;
import java.util.List;

public class JavaPmdCodeAnalysisSettingDto {
    CodeSourceDirectoryType codeSourceDirectoryType;

    String description;

    List<String> sourceCodePathList = new ArrayList<>();

    List<String> rulesetPathList = new ArrayList<>();

    public JavaPmdCodeAnalysisSettingDto(CodeSourceDirectoryType codeSourceDirectoryType) {
        this.codeSourceDirectoryType = codeSourceDirectoryType;

    }

    public CodeSourceDirectoryType getCodeAnalyzerType() {
        return codeSourceDirectoryType;
    }

    public void setCodeAnalyzerType(CodeSourceDirectoryType codeSourceDirectoryType) {
        this.codeSourceDirectoryType = codeSourceDirectoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSourceCodePathList() {
        return sourceCodePathList;
    }

    public void setSourceCodePathList(List<String> sourceCodePathList) {
        this.sourceCodePathList = sourceCodePathList;
    }

    public List<String> getRulesetPathList() {
        return rulesetPathList;
    }

    public void setRulesetPathList(List<String> rulesetPathList) {
        this.rulesetPathList = rulesetPathList;
    }

    @Override
    public String toString() {
        return "type=" + codeSourceDirectoryType + ", desc='" + description ;
    }
}
