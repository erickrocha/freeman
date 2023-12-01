package com.erocha.freeman.commons.exceptions;

public class UnsupportedFileExtension extends BusinessException {

    public UnsupportedFileExtension(String message) {
        super(message);
    }

    public UnsupportedFileExtension() {
        super("unsupported.file");
    }
}
