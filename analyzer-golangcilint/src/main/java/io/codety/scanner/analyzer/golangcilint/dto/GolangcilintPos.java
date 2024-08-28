package io.codety.scanner.analyzer.golangcilint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GolangcilintPos {
    @JsonProperty("Filename") 
    private String filename;
    @JsonProperty("Offset") 
    private int offset;
    @JsonProperty("Line") 
    private int line;
    @JsonProperty("Column") 
    private int column;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

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
}
