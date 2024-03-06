package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * configure the filterchain
     *
     * @param http httpSecurity to configure
     * @return security filterchain configured
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/bidList/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/curvePoint/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/rating/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/ruleName/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/trade/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/user/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .permitAll()
                        .failureUrl("/error")
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                )
                .rememberMe(Customizer.withDefaults());
        return http.build();
    }

    /**
     *  Bean for encode password with BCrypt
     *
     * @return an instance of passwordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *  Configure and return the authentificationManager use for auth user
     *
     * @param http use for acces authentificationManager
     * @param bCryptPasswordEncoder Encodeur for secure password
     * @return authentificationManager configure
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

}
