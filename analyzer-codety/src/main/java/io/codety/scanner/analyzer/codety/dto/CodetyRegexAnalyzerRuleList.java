package io.codety.scanner.analyzer.codety.dto;

import java.util.ArrayList;
import java.util.List;

public class CodetyRegexAnalyzerRuleList {

    private List<CodetyRegexAnalyzerRule> rules = new ArrayList<>();

    public List<CodetyRegexAnalyzerRule> getRules() {
        return rules;
    }

    public void setRules(List<CodetyRegexAnalyzerRule> rules) {
        this.rules = rules;
    }
}
