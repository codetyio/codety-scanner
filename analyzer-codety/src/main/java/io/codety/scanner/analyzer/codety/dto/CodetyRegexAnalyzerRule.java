package io.codety.scanner.analyzer.codety.dto;

public class CodetyRegexAnalyzerRule {

    private String regex;
    private String issueCode;
    private String issueCategory;
    private String description;
    private Integer priority;


    public CodetyRegexAnalyzerRule(String regex) {
        this.regex = regex;
    }

    public CodetyRegexAnalyzerRule() {
    }

    public String getIssueCode() {
        return issueCode;
    }

    public void setIssueCode(String issueCode) {
        this.issueCode = issueCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
