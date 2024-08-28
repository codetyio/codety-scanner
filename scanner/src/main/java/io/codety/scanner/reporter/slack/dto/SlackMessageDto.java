package io.codety.scanner.reporter.slack.dto;

import java.util.ArrayList;

public class SlackMessageDto {
    private String user;
    private String type;
    private String ts;
    private String bot_id;
    private String app_id;
    private String text;
    private String team;
    private SlackBotProfile bot_profile;
    private ArrayList<SlackMessageBlock> blocks;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBot_id() {
        return bot_id;
    }

    public void setBot_id(String bot_id) {
        this.bot_id = bot_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public SlackBotProfile getBot_profile() {
        return bot_profile;
    }

    public void setBot_profile(SlackBotProfile bot_profile) {
        this.bot_profile = bot_profile;
    }

    public ArrayList<SlackMessageBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<SlackMessageBlock> blocks) {
        this.blocks = blocks;
    }
}
