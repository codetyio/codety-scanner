package io.codety.scanner.analyzer.dto.materialized;

public class CodetyRulesetPullApiPayloadDto {

    private Integer codeAnalyzerType;// reference to class CodeAnalyzerType;
    private String language;
    private String pluginCode;
    private String payload;
    private boolean encoded;
    private String version;

    //maybe more parameters here if needed.====================================

    public Integer getCodeAnalyzerType() {
        return codeAnalyzerType;
    }

    public void setCodeAnalyzerType(Integer codeAnalyzerType) {
        this.codeAnalyzerType = codeAnalyzerType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(String pluginCode) {
        this.pluginCode = pluginCode;
    }

    public boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }
}
