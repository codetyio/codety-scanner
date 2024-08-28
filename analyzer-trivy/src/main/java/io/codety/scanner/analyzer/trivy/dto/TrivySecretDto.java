package io.codety.scanner.analyzer.trivy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrivySecretDto {
    @JsonProperty("RuleID") 
    private String ruleID;
    @JsonProperty("Category") 
    private String category;
    @JsonProperty("Severity") 
    private String severity;
    @JsonProperty("Title") 
    private String title;
    @JsonProperty("StartLine") 
    private int startLine;
    @JsonProperty("EndLine") 
    private int endLine;
//    @JsonProperty("Code")
//    private Code code;
    @JsonProperty("Match") 
    private String match;
//    @JsonProperty("Layer")
//    private Layer layer;

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

}
