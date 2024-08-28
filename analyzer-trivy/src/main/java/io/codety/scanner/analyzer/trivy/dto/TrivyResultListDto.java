package io.codety.scanner.analyzer.trivy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class TrivyResultListDto {
    @JsonProperty("SchemaVersion") 
    private int schemaVersion;
    @JsonProperty("CreatedAt") 
    private Date createdAt;
    @JsonProperty("ArtifactName") 
    private String artifactName;
    @JsonProperty("ArtifactType") 
    private String artifactType;
//    @JsonProperty("Metadata")
//    private Metadata metadata;
    @JsonProperty("Results") 
    private ArrayList<TrivyResultDto> results;

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public ArrayList<TrivyResultDto> getResults() {
        return results;
    }

    public void setResults(ArrayList<TrivyResultDto> results) {
        this.results = results;
    }
}
