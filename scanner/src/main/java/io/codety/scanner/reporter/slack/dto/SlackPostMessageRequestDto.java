package io.codety.scanner.reporter.slack.dto;

import java.util.List;

public class SlackPostMessageRequestDto {

    private String channel;
    private List<SlackMessageBlock> blocks;
    private String text;

    public SlackPostMessageRequestDto(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<SlackMessageBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<SlackMessageBlock> blocks) {
        this.blocks = blocks;
    }
}
