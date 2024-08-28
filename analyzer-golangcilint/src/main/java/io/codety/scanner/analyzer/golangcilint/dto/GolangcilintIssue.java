package io.codety.scanner.analyzer.golangcilint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GolangcilintIssue {
    @JsonProperty("FromLinter")
    private String fromLinter;
    @JsonProperty("Text") 
    private String text;
    @JsonProperty("Severity") 
    private String severity;
    @JsonProperty("SourceLines") 
    private ArrayList<String> sourceLines;
    @JsonProperty("Replacement") 
    private Object replacement;
    @JsonProperty("Pos") 
    private GolangcilintPos pos;
    @JsonProperty("ExpectNoLint") 
    private boolean expectNoLint;
    @JsonProperty("ExpectedNoLintLinter") 
    private String expectedNoLintLinter;

    public String getFromLinter() {
        return fromLinter;
    }

    public void setFromLinter(String fromLinter) {
        this.fromLinter = fromLinter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public ArrayList<String> getSourceLines() {
        return sourceLines;
    }

    public void setSourceLines(ArrayList<String> sourceLines) {
        this.sourceLines = sourceLines;
    }

    public Object getReplacement() {
        return replacement;
    }

    public void setReplacement(Object replacement) {
        this.replacement = replacement;
    }

    public GolangcilintPos getPos() {
        return pos;
    }

    public void setPos(GolangcilintPos pos) {
        this.pos = pos;
    }

    public boolean isExpectNoLint() {
        return expectNoLint;
    }

    public void setExpectNoLint(boolean expectNoLint) {
        this.expectNoLint = expectNoLint;
    }

    public String getExpectedNoLintLinter() {
        return expectedNoLintLinter;
    }

    public void setExpectedNoLintLinter(String expectedNoLintLinter) {
        this.expectedNoLintLinter = expectedNoLintLinter;
    }
}

