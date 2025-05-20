package com.devstack.healthCare.product.security;


import com.devstack.healthCare.product.jwt.JwtConfig;
import com.devstack.healthCare.product.jwt.JwtTokenVerifier;
import com.devstack.healthCare.product.jwt.jwtUsernamePasswordAuthenticationFilter;
import com.devstack.healthCare.product.service.impl.ApplicationUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import javax.crypto.SecretKey;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserServiceImpl applicationUserService;

//    private final UserService userService;

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserServiceImpl applicationUserService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowedOrigins(List.of("*")); // allowed all origin to access
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE")); // list of methods
        configuration.setExposedHeaders(List.of("Authorization")); // only allowed to expose Authorization header to set token , all other headers hided

        /* csrf disable */
        http.csrf().disable()
                .cors().configurationSource(request -> configuration)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(
                        new jwtUsernamePasswordAuthenticationFilter(authenticationManager()
                                ,jwtConfig,secretKey)
                )
                .addFilterAfter(new JwtTokenVerifier(jwtConfig,secretKey),
                        jwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/users/visitor/**",
                        "/api/v1/borrowers/**",
                        "/api/v1/books/**",
                        "/api/v1/loans/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
       DaoAuthenticationProvider daoAuthenticationProvider
               = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
       daoAuthenticationProvider.setUserDetailsService(applicationUserService);
       return daoAuthenticationProvider;
    }
}
