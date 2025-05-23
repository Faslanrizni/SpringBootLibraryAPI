package com.devstack.healthCare.product.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;

    private SecretKey secretKey; //final

    public JwtTokenVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authenticationHeader =
                request.getHeader(jwtConfig.getAuthorizationHeader());
        String to = authenticationHeader;
        if (Strings.isNullOrEmpty(authenticationHeader) ||
                !authenticationHeader.startsWith(jwtConfig.getTokenPrefix())
        ) {
        filterChain.doFilter(request,response);
        return;

        }
        String token = authenticationHeader.replace(jwtConfig.getTokenPrefix(),"");
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();

            var authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m->new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            /* keeps authenticated sessions
            * */
            SecurityContextHolder.getContext().setAuthentication(authentication);



        }catch (JwtException e){
            throw new IllegalStateException(String.format("token %S cannot be trusted",token));
        }
        filterChain.doFilter(request,response);

    }


}
