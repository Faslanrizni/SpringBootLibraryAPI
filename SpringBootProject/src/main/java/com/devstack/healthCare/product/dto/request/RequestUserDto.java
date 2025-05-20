package com.devstack.healthCare.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestUserDto {
    private long id;
    private String fullname;

    private String username;
    private String password;
}
