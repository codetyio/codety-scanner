package io.codety.scanner.analyzer.rubocop.dto;

public class RubocopSummary {
    private int offense_count;
    private int target_file_count;
    private int inspected_file_count;

    public int getOffense_count() {
        return offense_count;
    }

    public void setOffense_count(int offense_count) {
        this.offense_count = offense_count;
    }

    public int getTarget_file_count() {
        return target_file_count;
    }

    public void setTarget_file_count(int target_file_count) {
        this.target_file_count = target_file_count;
    }

    public int getInspected_file_count() {
        return inspected_file_count;
    }

    public void setInspected_file_count(int inspected_file_count) {
        this.inspected_file_count = inspected_file_count;
    }
}
