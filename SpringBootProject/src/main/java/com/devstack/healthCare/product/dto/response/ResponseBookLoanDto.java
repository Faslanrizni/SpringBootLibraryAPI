package com.devstack.healthCare.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookLoanDto {
    private long id;
    private ResponseBookDto book;
    private ResponseBorrowerDto borrower;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private boolean returned;
}
