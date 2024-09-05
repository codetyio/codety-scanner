package io.codety.scanner.analyzer.rubocop.dto;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class RubocopFile {
    private String path;
    private ArrayList<RubocopOffense> offenses;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<RubocopOffense> getOffenses() {
        return offenses;
    }

    public void setOffenses(ArrayList<RubocopOffense> offenses) {
        this.offenses = offenses;
    }
}

