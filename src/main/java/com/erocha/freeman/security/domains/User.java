package com.erocha.freeman.security.domains;

import com.erocha.freeman.app.domains.Menu;
import com.erocha.freeman.commons.utils.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Builder.Default
    private String id = UUIDGenerator.generate();
    private String email;
    private String password;
    private String name;
    private Profile profile;

    @Builder.Default
    private Boolean enabled = true;
    private Boolean firstLogin;

    @Singular("menu")
    private List<Menu> menus;

    @Override
    public Set getAuthorities() {
        switch (this.profile) {
            case SYSADMIN:
                return new HashSet<>(Collections.singletonList(Role.builder().authority(Profile.SYSADMIN.toString()).build()));
            case DEVELOPER:
                return new HashSet<>(Collections.singletonList(Role.builder().authority(Profile.DEVELOPER.toString()).build()));
            case PROJECT_MANAGER:
                return Stream.of(Role.builder().authority(Profile.PROJECT_MANAGER.toString()).build()).collect(Collectors.toSet());
            case FINANCIAL:
                return new HashSet<>(Collections.singletonList(Role.builder().authority(Profile.FINANCIAL.toString()).build()));
            default:
                return Collections.emptySet();
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
