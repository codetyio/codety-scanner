package io.codety.scanner.analyzer.stylelint.dto;

import java.util.List;

public class StylelintIssue {

    /*
    * "source": "/Users/user/git/codety-scanner/code-issue-examples/css/bad-css.css",
    "deprecations": [],
    "invalidOptionWarnings": [],
    "parseErrors": [],
    "errored": true,
    "warnings": [
    * */
    private String source;
    private Boolean errored;
    List<StylelintIssueWarning> warnings;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getErrored() {
        return errored;
    }

    public void setErrored(Boolean errored) {
        this.errored = errored;
    }

    public List<StylelintIssueWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<StylelintIssueWarning> warnings) {
        this.warnings = warnings;
    }
}
