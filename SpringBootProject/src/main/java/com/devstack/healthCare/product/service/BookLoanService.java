package com.devstack.healthCare.product.service;

import com.devstack.healthCare.product.dto.request.RequestBookLoanDto;
import com.devstack.healthCare.product.dto.response.ResponseBookLoanDto;

import java.util.List;

public interface BookLoanService {
    void borrowBook(RequestBookLoanDto dto);
    void returnBook(long bookId, long borrowerId);
    List<ResponseBookLoanDto> getCurrentLoansForBorrower(long borrowerId);
    List<ResponseBookLoanDto> getAllLoans(int page, int size);
}
