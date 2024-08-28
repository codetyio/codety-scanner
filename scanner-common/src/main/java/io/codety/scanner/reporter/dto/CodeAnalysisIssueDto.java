package io.codety.scanner.reporter.dto;

public class CodeAnalysisIssueDto {

    String filePath;
    Integer startLineNumber;
    String issueCategory;//e.g. error prone, style, etc.
    String issueCode; // e.g. UnusedImportClass.
    Integer priority;
    String description;
    String packagePath;
    Integer endLineNumber;
    Integer cweId; // reference:  https://cwe.mitre.org/

    public CodeAnalysisIssueDto() {
    }

    public CodeAnalysisIssueDto(String filePath, Integer startLineNumber, String issueCategory, String issueCode, Integer priority, String description) {
        this.filePath = filePath;
        this.startLineNumber = startLineNumber;
        this.issueCategory = issueCategory;
        this.issueCode = issueCode;
        this.priority = priority;
        this.description = description;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getStartLineNumber() {
        return startLineNumber;
    }

    public void setStartLineNumber(Integer startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getCweId() {
        return cweId;
    }

    public void setCweId(Integer cweId) {
        this.cweId = cweId;
    }

    public Integer getEndLineNumber() {
        return endLineNumber;
    }

    public void setEndLineNumber(Integer endLineNumber) {
        this.endLineNumber = endLineNumber;
    }
}
