package io.codety.scanner.util;


public class AnalyzerMatchingRule {

    public AnalyzerMatchingRule(CodeSourceDirectoryType codeSourceDirectoryType, String path) {
        this.codeSourceDirectoryType = codeSourceDirectoryType;
        this.path = path;
    }

    CodeSourceDirectoryType codeSourceDirectoryType;
    String path;


    public CodeSourceDirectoryType getCodeAnalyzerType() {
        return codeSourceDirectoryType;
    }

    public void setCodeAnalyzerType(CodeSourceDirectoryType codeSourceDirectoryType) {
        this.codeSourceDirectoryType = codeSourceDirectoryType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
