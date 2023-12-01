package com.erocha.freeman.app.domains;

import com.erocha.freeman.commons.utils.UUIDGenerator;
import com.erocha.freeman.security.domains.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class Menu implements Serializable {

    @Id
    @Builder.Default
    private String id = UUIDGenerator.generate();
    private Integer order;
    private String name;
    private String label;
    private String route;
    private Set<Profile> roles;

    @Builder.Default
    private boolean collapse = false;

    @Singular
    private List<Menu> children;

    public Boolean isLink() {
        return route != null && !route.isEmpty();
    }

    public Boolean isGroupMenu() {
        return this.children != null && !this.children.isEmpty();
    }

}
