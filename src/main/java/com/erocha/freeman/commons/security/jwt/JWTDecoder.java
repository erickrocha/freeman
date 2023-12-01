package com.erocha.freeman.commons.security.jwt;

import com.erocha.freeman.commons.security.filter.SecurityConstants;
import com.erocha.freeman.security.domains.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.erocha.freeman.commons.security.filter.SecurityConstants.TOKEN_AUDIENCE;
import static com.erocha.freeman.commons.security.filter.SecurityConstants.TOKEN_ISSUER;
import static com.erocha.freeman.commons.security.filter.SecurityConstants.TOKEN_TYPE;

@Component
public class JWTDecoder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${freeman.security.jwt.secret}")
    private String jwtSecret = SecurityConstants.SECRET;

    @Value("${freeman.security.jwt.expirationTime}")
    private Long expirationTime = SecurityConstants.EXPIRATION_TIME;

    public Claims getAllClaimsFromToken(String token) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtSecret.getBytes());
        Key key = Keys.hmacShaKeyFor(secretKeyBytes);
        return Jwts.parserBuilder().setSigningKey(key).requireAudience(TOKEN_AUDIENCE).build().parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) {

        if (authentication.getPrincipal() instanceof OidcUser) {
            DefaultOidcUser user = (DefaultOidcUser) authentication.getPrincipal();
            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            return execute(roles, user.getPreferredUsername(), user.getName(), user.getEmail());
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            List<String> roles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            return execute(roles, user.getName(), user.getName(), user.getName());
        } else if (authentication.getPrincipal() instanceof User user) {
            List<String> roles = user.getAuthorities().stream().toList();
            return execute(roles, user.getUsername(), user.getName(), user.getEmail());
        }
        return null;
    }

    private String execute(List<String> authorities, String username, String name, String email) {
        byte[] secretKeyBytes = Base64.getEncoder().encode(jwtSecret.getBytes());

        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(secretKeyBytes), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expirationTime).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setId(UUID.randomUUID().toString())
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(username)
                .claim(SecurityConstants.ROLES, authorities)
                .claim("name", name)
                .claim("preferred_username", username)
                .claim("email", email)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
