package com.erocha.freeman.config.security;

import com.erocha.freeman.commons.security.filter.JWTAuthenticationFilter;
import com.erocha.freeman.commons.security.filter.JWTAuthorizationFilter;
import com.erocha.freeman.commons.security.oauth2.CustomOAuth2UserService;
import com.erocha.freeman.commons.security.oauth2.FreemanAuthenticationSuccessHandler;
import com.erocha.freeman.security.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class FreemanSecurity {

    private final UserGateway userGateway;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authReq -> authReq.requestMatchers(
                antMatcher("/assets/**"),
                        antMatcher("/webjars/**"),
                        antMatcher("/login"),
                        antMatcher("/public/**"),
                        antMatcher("/auth/**"),
                        antMatcher("/error"),
                        antMatcher("/csrf"),
                        antMatcher("/swagger-ui.html"),
                        antMatcher("/swagger-ui/**"),
                        antMatcher("/v3/api-docs"),
                        antMatcher("/v3/api-docs/**")).permitAll()
                .anyRequest().authenticated()).logout(l -> l.logoutSuccessUrl("/").permitAll())
                .addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userGateway);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:8080");
        configuration.addAllowedOrigin("http://localhost");
        allowedHeaders(configuration);
        exposedHeaders(configuration);
        allowedMethod(configuration);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(180L);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void allowedHeaders(CorsConfiguration conf) {
        conf.addAllowedHeader("Accept");
        conf.addAllowedHeader("Accept-Language");
        conf.addAllowedHeader("Content-Language");
        conf.addAllowedHeader("Content-Type");
        conf.addAllowedHeader("DPR");
        conf.addAllowedHeader("Save-Data");
        conf.addAllowedHeader("Viewport-Width");
        conf.addAllowedHeader("Width");
        conf.addAllowedHeader("Authorization");
        conf.addAllowedHeader("Access-Control-Allow-Headers");
        conf.addAllowedHeader("Access-Control-Allow-Origin");
    }

    private void exposedHeaders(CorsConfiguration conf) {
        conf.addExposedHeader("Accept");
        conf.addExposedHeader("Accept-Language");
        conf.addExposedHeader("Content-Language");
        conf.addExposedHeader("Content-Type");
        conf.addExposedHeader("DPR");
        conf.addExposedHeader("Save-Data");
        conf.addExposedHeader("Viewport-Width");
        conf.addExposedHeader("Width");
        conf.addExposedHeader("Authorization");
        conf.addExposedHeader("Access-Control-Allow-Headers");
        conf.addExposedHeader("Access-Control-Allow-Origin");
    }

    private void allowedMethod(CorsConfiguration conf) {
        conf.addAllowedMethod(HttpMethod.GET);
        conf.addAllowedMethod(HttpMethod.POST);
        conf.addAllowedMethod(HttpMethod.DELETE);
        conf.addAllowedMethod(HttpMethod.PUT);
        conf.addAllowedMethod(HttpMethod.PATCH);
        conf.addAllowedMethod(HttpMethod.OPTIONS);
        conf.addAllowedMethod(HttpMethod.HEAD);
        conf.addAllowedMethod(HttpMethod.TRACE);
    }

}
