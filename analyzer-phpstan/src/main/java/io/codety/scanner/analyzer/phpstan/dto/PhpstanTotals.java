package io.codety.scanner.analyzer.phpstan.dto;

public class PhpstanTotals {
    private int errors;
    private int file_errors;

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getFile_errors() {
        return file_errors;
    }

    public void setFile_errors(int file_errors) {
        this.file_errors = file_errors;
    }
}
