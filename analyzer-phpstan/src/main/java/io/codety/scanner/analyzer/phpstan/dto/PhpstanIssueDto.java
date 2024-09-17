package io.codety.scanner.analyzer.phpstan.dto;

import java.util.ArrayList;

public class PhpstanIssueDto {
    private int errors;
    private ArrayList<PhpstanMessage> messages;

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public ArrayList<PhpstanMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<PhpstanMessage> messages) {
        this.messages = messages;
    }
}
