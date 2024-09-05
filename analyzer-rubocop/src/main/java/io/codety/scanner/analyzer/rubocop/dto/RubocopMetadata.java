package io.codety.scanner.analyzer.rubocop.dto;

public class RubocopMetadata {
    private String rubocop_version;
    private String ruby_engine;
    private String ruby_version;
    private String ruby_patchlevel;
    private String ruby_platform;

    public String getRubocop_version() {
        return rubocop_version;
    }

    public void setRubocop_version(String rubocop_version) {
        this.rubocop_version = rubocop_version;
    }

    public String getRuby_engine() {
        return ruby_engine;
    }

    public void setRuby_engine(String ruby_engine) {
        this.ruby_engine = ruby_engine;
    }

    public String getRuby_version() {
        return ruby_version;
    }

    public void setRuby_version(String ruby_version) {
        this.ruby_version = ruby_version;
    }

    public String getRuby_patchlevel() {
        return ruby_patchlevel;
    }

    public void setRuby_patchlevel(String ruby_patchlevel) {
        this.ruby_patchlevel = ruby_patchlevel;
    }

    public String getRuby_platform() {
        return ruby_platform;
    }

    public void setRuby_platform(String ruby_platform) {
        this.ruby_platform = ruby_platform;
    }
}
