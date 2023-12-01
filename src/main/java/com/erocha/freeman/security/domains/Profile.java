package com.erocha.freeman.security.domains;

import java.util.Arrays;
import java.util.List;

public enum Profile {

    PROJECT_MANAGER("Project Manager"), FINANCIAL("Financial"), DEVELOPER("Freelancer"), SYSADMIN("Sys Admin");

    private String label;

    Profile(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static List<Profile> getAll() {
        return Arrays.asList(Profile.values());
    }
}
