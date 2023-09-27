package com.example.G4_DailyReport.config;

import com.example.G4_DailyReport.helper.MySimpleUrlAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
    @Bean
    //Hashing password
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("is deprecated and marked for removal")
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/manager/**").hasAuthority("MANAGER")
                .requestMatchers("/**").hasAnyAuthority("USER","MANAGER","ADMIN")
                .and().cors().and().csrf()
                .and().headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler())
                .and()
                .logout();
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}