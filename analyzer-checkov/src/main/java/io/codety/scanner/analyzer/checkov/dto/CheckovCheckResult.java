package io.codety.scanner.analyzer.checkov.dto;

import java.util.ArrayList;

public class CheckovCheckResult {
    private String result;
    private ArrayList<String> evaluated_keys;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<String> getEvaluated_keys() {
        return evaluated_keys;
    }

    public void setEvaluated_keys(ArrayList<String> evaluated_keys) {
        this.evaluated_keys = evaluated_keys;
    }
}
