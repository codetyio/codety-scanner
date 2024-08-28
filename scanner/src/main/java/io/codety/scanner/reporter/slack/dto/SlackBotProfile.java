package io.codety.scanner.reporter.slack.dto;

public class SlackBotProfile {
    private String id;
    private String app_id;
    private String name;
    private SlackMessageIcons icons;
    private boolean deleted;
    private int updated;
    private String team_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SlackMessageIcons getIcons() {
        return icons;
    }

    public void setIcons(SlackMessageIcons icons) {
        this.icons = icons;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getUpdated() {
        return updated;
    }

    public void setUpdated(int updated) {
        this.updated = updated;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
}
