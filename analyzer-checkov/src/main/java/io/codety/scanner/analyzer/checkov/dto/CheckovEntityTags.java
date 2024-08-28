package io.codety.scanner.analyzer.checkov.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckovEntityTags {
    @JsonProperty("Name") 
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
