package com.devstack.healthCare.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDto {
    private long id;
    private String isbn;
    private String title;
    private String author;
    private boolean isAvailable;
}
