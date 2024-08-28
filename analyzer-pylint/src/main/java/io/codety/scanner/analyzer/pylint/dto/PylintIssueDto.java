package io.codety.scanner.analyzer.pylint.dto;

public class PylintIssueDto {
    /*
        "type": "convention",
        "module": "asteroid.dsp",
        "obj": "",
        "line": 1,
        "column": 0,
        "endLine": null,
        "endColumn": null,
        "path": "dsp/__init__.py",
        "symbol": "missing-module-docstring",
        "message": "Missing module docstring",
        "message-id": "C0114"
    },
    * */

    private String type;
    private String module;
    private String obj;
    private Integer line;
    private Integer column;
    private Integer endLine;
    private Integer endColumn;
    private String path;
    private String symbol;
    private String message;
    private String messageId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(Integer endColumn) {
        this.endColumn = endColumn;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
