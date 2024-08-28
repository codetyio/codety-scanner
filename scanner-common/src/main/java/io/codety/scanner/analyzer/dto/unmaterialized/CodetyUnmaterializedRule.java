package io.codety.scanner.analyzer.dto.unmaterialized;

public class CodetyUnmaterializedRule {
    String ruleId; //externalRulId
    CodetyUnmaterializedRuleSetting[] settings;

    public CodetyUnmaterializedRule() {
    }

    public CodetyUnmaterializedRule(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public CodetyUnmaterializedRuleSetting[] getSettings() {
        return settings;
    }

    public void setSettings(CodetyUnmaterializedRuleSetting[] settings) {
        this.settings = settings;
    }
}
