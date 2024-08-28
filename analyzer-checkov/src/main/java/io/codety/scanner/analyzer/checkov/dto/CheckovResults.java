package io.codety.scanner.analyzer.checkov.dto;

import java.util.ArrayList;

public class CheckovResults {
    private ArrayList<CheckovFailedCheck> failed_checks;

    public ArrayList<CheckovFailedCheck> getFailed_checks() {
        return failed_checks;
    }

    public void setFailed_checks(ArrayList<CheckovFailedCheck> failed_checks) {
        this.failed_checks = failed_checks;
    }
}
