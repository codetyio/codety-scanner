package io.codety.scanner.analyzer.rubocop.dto;

public class RubocopIssueLocation {
    private Integer start_line;
    private Integer start_column;
    private Integer last_line;
    private Integer last_column;
    private Integer length;
    private Integer line;
    private Integer column;

    public Integer getStart_line() {
        return start_line;
    }

    public void setStart_line(Integer start_line) {
        this.start_line = start_line;
    }

    public Integer getStart_column() {
        return start_column;
    }

    public void setStart_column(Integer start_column) {
        this.start_column = start_column;
    }

    public Integer getLast_line() {
        return last_line;
    }

    public void setLast_line(Integer last_line) {
        this.last_line = last_line;
    }

    public Integer getLast_column() {
        return last_column;
    }

    public void setLast_column(Integer last_column) {
        this.last_column = last_column;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
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
}
