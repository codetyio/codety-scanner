package io.codety.scanner.reporter.slack.dto;

import java.util.ArrayList;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class SlackMessageBlock {
    private String type;
    private String block_id;
    private ArrayList<SlackMessageElement> elements;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public ArrayList<SlackMessageElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<SlackMessageElement> elements) {
        this.elements = elements;
    }
}

