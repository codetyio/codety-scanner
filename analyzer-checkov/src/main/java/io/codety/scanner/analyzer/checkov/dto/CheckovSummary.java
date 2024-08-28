package io.codety.scanner.analyzer.checkov.dto;

public class CheckovSummary {
    private int passed;
    private int failed;
    private int skipped;
    private int parsing_errors;
    private int resource_count;
    private String checkov_version;

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(int skipped) {
        this.skipped = skipped;
    }

    public int getParsing_errors() {
        return parsing_errors;
    }

    public void setParsing_errors(int parsing_errors) {
        this.parsing_errors = parsing_errors;
    }

    public int getResource_count() {
        return resource_count;
    }

    public void setResource_count(int resource_count) {
        this.resource_count = resource_count;
    }

    public String getCheckov_version() {
        return checkov_version;
    }

    public void setCheckov_version(String checkov_version) {
        this.checkov_version = checkov_version;
    }
}
