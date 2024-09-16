package io.codety.scanner.analyzer.eslint.dto;

public class EslintResult {

    private String filePath;
    private EslintErrorMessage[] messages;
    private EslintDeprecatedRule[] usedDeprecatedRules;
    //"suppressedMessages": [],
    private Integer errorCount = 0;
    private Integer fatalErrorCount = 0;
    private Integer warningCount = 0;
    private Integer fixableErrorCount = 0;
    private Integer fixableWarningCount = 0;
    private String source;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public EslintErrorMessage[] getMessages() {
        return messages;
    }

    public void setMessages(EslintErrorMessage[] messages) {
        this.messages = messages;
    }

    public EslintDeprecatedRule[] getUsedDeprecatedRules() {
        return usedDeprecatedRules;
    }

    public void setUsedDeprecatedRules(EslintDeprecatedRule[] usedDeprecatedRules) {
        this.usedDeprecatedRules = usedDeprecatedRules;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getFatalErrorCount() {
        return fatalErrorCount;
    }

    public void setFatalErrorCount(Integer fatalErrorCount) {
        this.fatalErrorCount = fatalErrorCount;
    }

    public Integer getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(Integer warningCount) {
        this.warningCount = warningCount;
    }

    public Integer getFixableErrorCount() {
        return fixableErrorCount;
    }

    public void setFixableErrorCount(Integer fixableErrorCount) {
        this.fixableErrorCount = fixableErrorCount;
    }

    public Integer getFixableWarningCount() {
        return fixableWarningCount;
    }

    public void setFixableWarningCount(Integer fixableWarningCount) {
        this.fixableWarningCount = fixableWarningCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
