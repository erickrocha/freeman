package com.erocha.freeman.app.domains;


import org.springframework.http.HttpStatus;

public class ResponseBuilder {

    private final Response response;

    private ResponseBuilder(int status) {
        this.response = new Response(status);
    }

    public static ResponseBuilder as(int status) {
        return new ResponseBuilder(status);
    }

    public ResponseBuilder as(HttpStatus httpStatus) {
        return new ResponseBuilder(httpStatus.value());
    }


    public ResponseBuilder withMessage(String message) {
        this.response.setMessage(message);
        return this;
    }

    public Response build() {
        return response;
    }
}
