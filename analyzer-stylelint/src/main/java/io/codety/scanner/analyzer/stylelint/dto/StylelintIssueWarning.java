package io.codety.scanner.analyzer.stylelint.dto;

public class StylelintIssueWarning {
    /*
    * "line": 1,
        "column": 6,
        "endLine": 2,
        "endColumn": 2,
        "rule": "block-no-empty",
        "severity": "error",
        "text": "Unexpected empty block (block-no-empty)"
    * */
    private int line;
    private int column;
    private int endLine;
    private int endColumn;
    private String rule;
    private String severity;
    private String text;

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
