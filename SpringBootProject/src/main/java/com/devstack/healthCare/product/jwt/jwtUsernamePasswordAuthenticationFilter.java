package com.devstack.healthCare.product.jwt;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class jwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JwtConfig jwtConfig;

    private final SecretKey secretKey;


    public jwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, SecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }
    /* get the username and password when request to authenticate */


    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthenticationRequest
                    usernamePasswordAuthenticationRequest =
                    new ObjectMapper().readValue(
                            request.getInputStream(),
                    UsernamePasswordAuthenticationRequest.class);

            Authentication  authentication = new UsernamePasswordAuthenticationToken(
                    /* pass the username and password to authentication token
                    * */
                    usernamePasswordAuthenticationRequest.getUsername(),
                    usernamePasswordAuthenticationRequest.getPassword()
            );

            /* verify the authenticate object*/
            Authentication authenticate = authenticationManager.authenticate(authentication);

            return authenticate;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        /* if succeed the above method this function will triggerd
        * */

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDate())))
                .signWith(secretKey)
                .compact();
        response.addHeader(jwtConfig.getAuthorizationHeader(),
                jwtConfig.getTokenPrefix()+token);

    }
}
