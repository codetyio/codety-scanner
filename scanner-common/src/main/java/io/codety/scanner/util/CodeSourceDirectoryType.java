package io.codety.scanner.util;

public enum CodeSourceDirectoryType {

    source_code("source code"),
    test_case_code("test case code");
    public final String label;

    private CodeSourceDirectoryType(String label) {
        this.label = label;
    }

}
