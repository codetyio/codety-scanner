package io.codety.scanner.analyzer.shellcheck.dto;

public class ShellcheckIssue {

    /*
    *  "file": "codety-scanner/code-issue-examples/shell/bad-shell.sh",
    "line": 5,
    "endLine": 5,
    "column": 1,
    "endColumn": 1,
    "level": "error",
    "code": 1073,
    "message": "Couldn't parse this for loop. Fix to allow more checks.",
    "fix": null
    * */
    private String file;
    private Integer line;
    private Integer endLine;
    private Integer column;
    private Integer endColumn;
    private Integer code;
    private String level;
    private String message;
//    private String fix;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
