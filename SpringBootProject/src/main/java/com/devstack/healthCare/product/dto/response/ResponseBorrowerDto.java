package com.devstack.healthCare.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBorrowerDto {
    private long id;
    private String name;
    private String address;
    private String contact;

}
