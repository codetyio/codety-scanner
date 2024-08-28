package io.codety.scanner.analyzer.cppcheck.dto;

public class CppcheckCmdIssue {
//cppcheck  analyzer  --template="{id}_{cwe}_{file}_{line}_{severity}_{message}"
// --template-location="@@@@@{file}@@@@@{line}@@@@@{info}"  2> error-test-default.txt
    private String issueId;
    private Integer cwe;
    private String file;
    private Integer line;
    private Integer column;
    private String severity;
    private String message;
    private String locationIssueInfo;

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Integer getCwe() {
        return cwe;
    }

    public void setCwe(Integer cwe) {
        this.cwe = cwe;
    }

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

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocationIssueInfo() {
        return locationIssueInfo;
    }

    public void setLocationIssueInfo(String locationIssueInfo) {
        this.locationIssueInfo = locationIssueInfo;
    }
}
