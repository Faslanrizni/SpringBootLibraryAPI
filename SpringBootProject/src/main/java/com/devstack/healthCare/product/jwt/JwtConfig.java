package com.devstack.healthCare.product.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDate;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterDate() {
        return tokenExpirationAfterDate;
    }

    public void setTokenExpirationAfterDate(Integer tokenExpirationAfterDate) {
        this.tokenExpirationAfterDate = tokenExpirationAfterDate;
    }
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
