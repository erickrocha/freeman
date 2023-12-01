package com.erocha.freeman.commons.exceptions;

import java.io.Serializable;

public class ResourceNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super("resource.not.found");
    }

}
