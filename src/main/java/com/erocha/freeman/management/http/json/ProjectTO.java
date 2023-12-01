package com.erocha.freeman.management.http.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectTO {

    private String id;
    private String name;
    private String code;
    private ProjectManagerTO manager;
    private OwnerTO owner;
    private Set<String> phases;
    private boolean active;
}
