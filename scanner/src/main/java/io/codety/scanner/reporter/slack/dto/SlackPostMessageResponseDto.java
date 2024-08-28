package io.codety.scanner.reporter.slack.dto;

public class SlackPostMessageResponseDto {
    private boolean ok;
    private String channel;
    private String ts;
    private SlackMessageDto message;
    String error;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public SlackMessageDto getMessage() {
        return message;
    }

    public void setMessage(SlackMessageDto message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
