package io.codety.scanner.analyzer.dto;


import io.codety.common.dto.CodeAnalyzerType;

import java.io.File;

public class AnalyzerConfigurationDetailDto {
    String payload;
    String pluginCode;
    File file;
    String language;
    CodeAnalyzerType codeAnalyzerType; 

    public AnalyzerConfigurationDetailDto(String language, CodeAnalyzerType codeAnalyzerType) {
        this.language = language;
        this.codeAnalyzerType = codeAnalyzerType;
    }

    public AnalyzerConfigurationDetailDto() {
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public CodeAnalyzerType getCodeAnalyzerType() {
        return codeAnalyzerType;
    }

    public void setCodeAnalyzerType(CodeAnalyzerType codeAnalyzerType) {
        this.codeAnalyzerType = codeAnalyzerType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(String pluginCode) {
        this.pluginCode = pluginCode;
    }

}
