package com.erocha.freeman.app.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Response {

    private int status;
    private String message;
    private String uri;

    public Response(int status) {
        this.status = status;
    }
}
