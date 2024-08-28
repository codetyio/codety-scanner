package io.codety.scanner.analyzer.dto.unmaterialized;

public class CodetyUnmaterializedPluginGroup {

    String codeAnalyzerPluginCode; //plugin name, e.g. typescript-eslint, eslint-plugin-jsdoc
    CodetyUnmaterializedRule[] rules;

    public String getCodeAnalyzerPluginCode() {
        return codeAnalyzerPluginCode;
    }

    public void setCodeAnalyzerPluginCode(String codeAnalyzerPluginCode) {
        this.codeAnalyzerPluginCode = codeAnalyzerPluginCode;
    }

    public CodetyUnmaterializedRule[] getRules() {
        return rules;
    }

    public void setRules(CodetyUnmaterializedRule[] rules) {
        this.rules = rules;
    }
}
