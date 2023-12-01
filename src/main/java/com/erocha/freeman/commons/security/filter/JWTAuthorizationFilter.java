package com.erocha.freeman.commons.security.filter;

import com.erocha.freeman.commons.exceptions.BadCredentialsException;
import com.erocha.freeman.commons.security.jwt.JWTDecoder;
import com.erocha.freeman.security.domains.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.erocha.freeman.commons.security.filter.SecurityConstants.HEADER_STRING;
import static com.erocha.freeman.commons.security.filter.SecurityConstants.TOKEN_PREFIX;
import static java.util.Objects.nonNull;

@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(req);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(req, res);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        JWTDecoder jwtDecoder = new JWTDecoder();
        String authToken = request.getHeader(HEADER_STRING);

        String username = jwtDecoder.getUsernameFromToken(authToken);
        try {
            if (username != null && jwtDecoder.validateToken(authToken)) {
                Claims claims = jwtDecoder.getAllClaimsFromToken(authToken);
                List<Map<String, String>> rolesMap = claims.get(SecurityConstants.ROLES, List.class);
                if (nonNull(rolesMap) && !rolesMap.isEmpty()) {
                    List<String> strings = new ArrayList<>();
                    rolesMap.get(0).forEach((key, value) -> strings.add(value));
                    List<Role> roles = strings.stream().map(Role::new).toList();
                    return new UsernamePasswordAuthenticationToken(username, null,
                            roles.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).toList());
                }
                throw new BadCredentialsException("Invalid token");
            }
        } catch (ExpiredJwtException ex) {
            throw new BadCredentialsException(ex.getMessage());
        } catch (Exception ex) {
            log.error("Invalid token");
            throw new BadCredentialsException(ex.getMessage());
        }
        return null;
    }

}
