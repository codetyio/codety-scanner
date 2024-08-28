package io.codety.scanner.analyzer.trivy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TrivyResultDto {
    @JsonProperty("Target") 
    private String target;
    @JsonProperty("Class") 
    private String classObj;
    @JsonProperty("Secrets") 
    private ArrayList<TrivySecretDto> secrets;

    @JsonProperty("Vulnerabilities")
    private ArrayList<TrivyVulnerabilityDto> vulnerabilities;

    @JsonProperty("Misconfigurations")
    private ArrayList<TrivyMisconfiguration> misconfigurations;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getClassObj() {
        return classObj;
    }

    public void setClassObj(String classObj) {
        this.classObj = classObj;
    }

    public ArrayList<TrivySecretDto> getSecrets() {
        return secrets;
    }

    public void setSecrets(ArrayList<TrivySecretDto> secrets) {
        this.secrets = secrets;
    }

    public ArrayList<TrivyVulnerabilityDto> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(ArrayList<TrivyVulnerabilityDto> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public ArrayList<TrivyMisconfiguration> getMisconfigurations() {
        return misconfigurations;
    }

    public void setMisconfigurations(ArrayList<TrivyMisconfiguration> misconfigurations) {
        this.misconfigurations = misconfigurations;
    }
}
