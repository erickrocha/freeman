package com.erocha.freeman.commons.utils;

import com.erocha.freeman.commons.exceptions.UnsupportedFileExtension;
import com.erocha.freeman.hr.domains.Avatar;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImageBase64Handler {

    private static final String DATA_IMAGE_PREFIX = "data:image/";
    private static final String BASE_SUFFIX = ";base64,";

    private ImageBase64Handler() {
    }

    public static String fromByteArrayToString(Avatar avatar) {
        String base64Str = Base64.getEncoder().encodeToString(avatar.getImage());
        StringBuilder builder = new StringBuilder(DATA_IMAGE_PREFIX);
        builder.append(avatar.getSuffix());
        builder.append(BASE_SUFFIX);
        return builder.append(base64Str).toString();
    }

    public static Avatar fromStringToByteArray(String base64Str) {
        String temp = base64Str.replace(DATA_IMAGE_PREFIX, "");
        String JPG = "jpg";
        String JPEG = "jpeg";
        String PNG = "png";
        if (temp.startsWith(JPG)) {
            String cleaned = temp.replace(JPG + BASE_SUFFIX, "");
            return new Avatar(JPG, Base64.getDecoder().decode(cleaned.getBytes(StandardCharsets.UTF_8)));
        } else if (temp.startsWith(JPEG)) {
            String cleaned = temp.replace(JPEG + BASE_SUFFIX, "");
            return new Avatar(JPEG, Base64.getDecoder().decode(cleaned.getBytes(StandardCharsets.UTF_8)));
        } else if (temp.startsWith(PNG)) {
            String cleaned = temp.replace(PNG + BASE_SUFFIX, "");
            return new Avatar(PNG, Base64.getDecoder().decode(cleaned.getBytes(StandardCharsets.UTF_8)));
        } else {
            throw new UnsupportedFileExtension("File type unsupported! Only png, jpg, jpeg");
        }
    }

}
