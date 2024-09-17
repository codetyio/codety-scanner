package io.codety.scanner.analyzer.phpstan.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhpstanRoot {
    private PhpstanTotals totals;
    private Map<String, PhpstanIssueDto> files = new HashMap<>();
    private ArrayList<Object> errors;

    public PhpstanTotals getTotals() {
        return totals;
    }

    public void setTotals(PhpstanTotals totals) {
        this.totals = totals;
    }

    public Map<String, PhpstanIssueDto> getFiles() {
        return files;
    }

    public void setFiles(Map<String, PhpstanIssueDto> files) {
        this.files = files;
    }

    public ArrayList<Object> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Object> errors) {
        this.errors = errors;
    }
}
