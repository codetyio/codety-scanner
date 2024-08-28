package io.codety.scanner.analyzer.golangcilint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GolangcilintReport {
    @JsonProperty("Warnings") 
    private ArrayList<GolangcilintWarning> warnings;
    @JsonProperty("Linters") 
    private ArrayList<GolangcilintLinter> linters;

    public ArrayList<GolangcilintWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(ArrayList<GolangcilintWarning> warnings) {
        this.warnings = warnings;
    }

    public ArrayList<GolangcilintLinter> getLinters() {
        return linters;
    }

    public void setLinters(ArrayList<GolangcilintLinter> linters) {
        this.linters = linters;
    }
}
