package it.uniromatre.breadexchange2_0.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static it.uniromatre.breadexchange2_0.role.Permission.*;
import static it.uniromatre.breadexchange2_0.role.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private static final String[] WHITE_LIST_URL = {

            "/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"


    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                // Admin

                                .requestMatchers("/admin/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/admin/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/admin/**").hasAnyAuthority(ADMIN_DELETE.name())

                                // Bakery

                                .requestMatchers("/bakery/**").hasAnyRole(ADMIN.name(), BAKERY_OWNER.name())
                                .requestMatchers(GET,"/bakery/**").hasAnyAuthority(ADMIN_READ.name(), BAKERY_OWNER.name())
                                .requestMatchers(POST, "/bakery/**").hasAnyAuthority(ADMIN_CREATE.name(), BAKERY_OWNER.name())
                                .requestMatchers(PUT, "/bakery/**").hasAnyAuthority(ADMIN_UPDATE.name(), BAKERY_OWNER.name())
                                .requestMatchers(DELETE, "/bakery/**").hasAnyAuthority(ADMIN_DELETE.name(), BAKERY_OWNER.name())

                                // Customers

                                .requestMatchers("/user/**").hasAnyRole(ADMIN.name(), CUSTOMER.name())
                                .requestMatchers(GET,"/user/**").hasAnyAuthority(ADMIN_READ.name(), CUSTOMER.name())
                                .requestMatchers(POST, "/user/**").hasAnyAuthority(ADMIN_CREATE.name(), CUSTOMER.name())
                                .requestMatchers(PUT, "/user/**").hasAnyAuthority(ADMIN_UPDATE.name(), CUSTOMER.name())
                                .requestMatchers(DELETE, "/user/**").hasAnyAuthority(ADMIN_DELETE.name(), CUSTOMER.name())

                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout( logout -> logout.logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                );

        return http.build();
    }






}



