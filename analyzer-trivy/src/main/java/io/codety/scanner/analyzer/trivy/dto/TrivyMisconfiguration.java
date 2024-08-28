package io.codety.scanner.analyzer.trivy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class TrivyMisconfiguration {
    @JsonProperty("Type") 
    private String type;
    @JsonProperty("ID") 
    private String iD;
    @JsonProperty("AVDID") 
    private String aVDID;
    @JsonProperty("Title") 
    private String title;
    @JsonProperty("Description") 
    private String description;
    @JsonProperty("Message") 
    private String message;
    @JsonProperty("Namespace") 
    private String namespace;
    @JsonProperty("Query") 
    private String query;
    @JsonProperty("Resolution") 
    private String resolution;
    @JsonProperty("Severity") 
    private String severity;
    @JsonProperty("PrimaryURL") 
    private String primaryURL;
    @JsonProperty("References") 
    private ArrayList<String> references;
    @JsonProperty("Status") 
    private String status;
//    @JsonProperty("Layer")
//    private Layer layer;
//    @JsonProperty("CauseMetadata")
//    private CauseMetadata causeMetadata;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getaVDID() {
        return aVDID;
    }

    public void setaVDID(String aVDID) {
        this.aVDID = aVDID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPrimaryURL() {
        return primaryURL;
    }

    public void setPrimaryURL(String primaryURL) {
        this.primaryURL = primaryURL;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}