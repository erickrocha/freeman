package com.erocha.freeman.app.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {

    private String code;
    private String value;
    private Severity severity;

    public Message(String code, String value, Severity severity) {
        this.code = code;
        this.value = value;
        this.severity = severity;
    }
}
