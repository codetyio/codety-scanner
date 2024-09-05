package io.codety.scanner.analyzer.rubocop.dto;

public class RubocopOffense {
    private String severity;
    private String message;
    private String cop_name;
    private boolean corrected;
    private boolean correctable;
    private RubocopIssueLocation location;

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

    public String getCop_name() {
        return cop_name;
    }

    public void setCop_name(String cop_name) {
        this.cop_name = cop_name;
    }

    public boolean isCorrected() {
        return corrected;
    }

    public void setCorrected(boolean corrected) {
        this.corrected = corrected;
    }

    public boolean isCorrectable() {
        return correctable;
    }

    public void setCorrectable(boolean correctable) {
        this.correctable = correctable;
    }

    public RubocopIssueLocation getLocation() {
        return location;
    }

    public void setLocation(RubocopIssueLocation location) {
        this.location = location;
    }
}
