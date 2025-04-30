package com.codetest.bookingsystem.config;

import com.codetest.bookingsystem.enums.Role;
import com.codetest.bookingsystem.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*  .requestMatchers("/api/v1/admin/").hasAuthority(Role.ADMIN.name())*/
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("api/auth/**", "api/auth/*", "/v2/api-docs",
                                "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
                                "/configuration/ui", "/configuration/security", "/swagger-ui/**",
                                "/swagger-ui.html", "/api/auth/**",
                                "/api/test/**")
                        .permitAll()
                        .requestMatchers("/swagger-ui/*", "/v3/api-docs/*").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/***", "/v3/api-docs/***").permitAll() // Allow Swagger UI and API docs
                        .requestMatchers("/api/admin").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/admin/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/admin/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/admin/*/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/admin/*/*/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/admin/*/*/*/*").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/user").hasAuthority(Role.USER.name())
                        .requestMatchers("/api/user/*").permitAll()
                        .requestMatchers("/api/user/*/*").permitAll()
                        .requestMatchers("/api/user/*/*/*").permitAll()
                        .requestMatchers("/api/user/*/*/*/*").permitAll()
                        .requestMatchers("/api/user/*/*/*/*/*").permitAll()

                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
