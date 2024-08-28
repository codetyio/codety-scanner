package io.codety.scanner.reporter.github.dto;

import java.util.Objects;

public class ValueDistributionDto {
    private String path;
    private int startLineNumber;

    public ValueDistributionDto(String path, int startLineNumber) {
        this.path = path;
        this.startLineNumber = startLineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueDistributionDto that = (ValueDistributionDto) o;
        return startLineNumber == that.startLineNumber && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, startLineNumber);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStartLineNumber() {
        return startLineNumber;
    }

    public void setStartLineNumber(int startLineNumber) {
        this.startLineNumber = startLineNumber;
    }
}
