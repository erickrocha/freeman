package com.erocha.freeman.commons.security.filter;

import com.erocha.freeman.security.domains.User;
import com.erocha.freeman.commons.security.jwt.JWTDecoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.erocha.freeman.commons.security.filter.SecurityConstants.HEADER_STRING;
import static com.erocha.freeman.commons.security.filter.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Value("${freeman.security.jwt.expirationTime}")
    private String expireIn = String.valueOf(SecurityConstants.EXPIRATION_TIME);


    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User user = getObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException {
        JWTDecoder jwtDecoder = new JWTDecoder();
        String token = jwtDecoder.generateToken(auth);
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader("Content-Type", "application/json");
        res.getWriter().write(buildBody(token, Long.valueOf(expireIn)));
    }

    private ObjectMapper getObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    private String buildBody(String token, long expireIn) {
        Map<String, String> map = new HashMap<>();
        map.put("type", TOKEN_PREFIX);
        map.put("token", token);
        map.put("expireIn", String.valueOf(expireIn));

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }
}
