package io.codety.scanner.analyzer.golangcilint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GolangcilintLinter{
    @JsonProperty("Name") 
    private String name;
    @JsonProperty("Enabled") 
    private boolean enabled;
    @JsonProperty("EnabledByDefault") 
    private boolean enabledByDefault;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }

    public void setEnabledByDefault(boolean enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
    }
}
