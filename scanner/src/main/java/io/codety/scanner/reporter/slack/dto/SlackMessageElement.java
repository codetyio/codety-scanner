package io.codety.scanner.reporter.slack.dto;

import java.util.ArrayList;

public class SlackMessageElement {
    private String type;
    private ArrayList<SlackMessageElement> elements;
    private String text;
    private String name;
    private String unicode;
    private String style;
    private String url;
    private Integer indent;

    public SlackMessageElement() {
    }

    public SlackMessageElement(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<SlackMessageElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<SlackMessageElement> elements) {
        this.elements = elements;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Integer getIndent() {
        return indent;
    }

    public void setIndent(Integer indent) {
        this.indent = indent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
