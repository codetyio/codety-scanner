package io.codety.scanner.analyzer.rubocop.dto;

import java.util.ArrayList;

public class RubocopRoot {
    private RubocopMetadata metadata;
    private ArrayList<RubocopFile> files;
    private RubocopSummary summary;

    public RubocopMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(RubocopMetadata metadata) {
        this.metadata = metadata;
    }

    public ArrayList<RubocopFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<RubocopFile> files) {
        this.files = files;
    }

    public RubocopSummary getSummary() {
        return summary;
    }

    public void setSummary(RubocopSummary summary) {
        this.summary = summary;
    }
}
