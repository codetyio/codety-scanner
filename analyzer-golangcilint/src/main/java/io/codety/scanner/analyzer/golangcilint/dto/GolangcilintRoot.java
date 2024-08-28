package io.codety.scanner.analyzer.golangcilint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GolangcilintRoot {
    @JsonProperty("Issues") 
    private ArrayList<GolangcilintIssue> issues;
    @JsonProperty("Report") 
    private GolangcilintReport report;

    public ArrayList<GolangcilintIssue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<GolangcilintIssue> issues) {
        this.issues = issues;
    }

    public GolangcilintReport getReport() {
        return report;
    }

    public void setReport(GolangcilintReport report) {
        this.report = report;
    }
}
