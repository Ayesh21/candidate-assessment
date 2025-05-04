package com.teleport.candidate_assessment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/** The type Security config. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Security filter chain security filter chain.
   *
   * @param http the http
   * @return the security filter chain
   * @throws Exception the exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .permitAll()
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("api/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated());
    return http.build();
  }

  /**
   * User details service user details service.
   *
   * @param passwordEncoder the password encoder
   * @return the user details service
   */
  @Bean
  public UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
    final UserDetails user =
        User.withUsername("admin")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();
    return new InMemoryUserDetailsManager(user);
  }

  /**
   * Password encoder password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
