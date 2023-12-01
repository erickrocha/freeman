package com.erocha.freeman.security.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
