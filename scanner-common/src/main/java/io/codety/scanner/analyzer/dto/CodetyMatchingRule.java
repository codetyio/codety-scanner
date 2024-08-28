package io.codety.scanner.analyzer.dto;

import java.util.regex.Pattern;

public class CodetyMatchingRule {

    private int priority;
    private String ruleId; //
    private String regex;
    private String issueShortDescription;
    private Pattern pattern = null;

    public CodetyMatchingRule(int priority, String ruleId, String regex) {
        this.priority = priority;
        this.ruleId = ruleId;
        this.regex = regex;
        this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getIssueShortDescription() {
        return issueShortDescription;
    }

    public void setIssueShortDescription(String issueShortDescription) {
        this.issueShortDescription = issueShortDescription;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
