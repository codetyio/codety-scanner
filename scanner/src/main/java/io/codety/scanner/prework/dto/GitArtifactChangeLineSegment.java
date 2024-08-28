package io.codety.scanner.prework.dto;

public class GitArtifactChangeLineSegment {
    public GitArtifactChangeLineSegment(int startLineInclusive, int endLineInclusive) {
        this.startLineInclusive = startLineInclusive;
        this.endLineInclusive = endLineInclusive;
    }

    int startLineInclusive;
    int endLineInclusive;

    public int getStartLineInclusive() {
        return startLineInclusive;
    }

    public void setStartLineInclusive(int startLineInclusive) {
        this.startLineInclusive = startLineInclusive;
    }

    public int getEndLineInclusive() {
        return endLineInclusive;
    }

    public void setEndLineInclusive(int endLineInclusive) {
        this.endLineInclusive = endLineInclusive;
    }
}
