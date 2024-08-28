package io.codety.scanner.analyzer.checkov.dto;

public class CheckovRoot {
    private String check_type;
    private CheckovResults results;
    private CheckovSummary summary;

    public String getCheck_type() {
        return check_type;
    }

    public void setCheck_type(String check_type) {
        this.check_type = check_type;
    }

    public CheckovResults getResults() {
        return results;
    }

    public void setResults(CheckovResults results) {
        this.results = results;
    }

    public CheckovSummary getSummary() {
        return summary;
    }

    public void setSummary(CheckovSummary summary) {
        this.summary = summary;
    }
}
