package com.devstack.healthCare.product.util.mapper;

import com.devstack.healthCare.product.dto.response.ResponseBookLoanDto;
import com.devstack.healthCare.product.entity.BookLoan;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapper.class, BorrowerMapper.class})
public interface BookLoanMapper {

    ResponseBookLoanDto toResponseBookLoanDto(BookLoan bookLoan);
    List<ResponseBookLoanDto> toResponseBookLoanDtoList(List<BookLoan> list);
}
