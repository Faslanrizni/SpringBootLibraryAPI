package com.devstack.healthCare.product.util.mapper;

import com.devstack.healthCare.product.dto.request.RequestBorrowerDto;
import com.devstack.healthCare.product.dto.response.ResponseBorrowerDto;
import com.devstack.healthCare.product.entity.Borrower;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BorrowerMapper {
    ResponseBorrowerDto toResponseBorrowerDto(Borrower borrower); /*entity => dto*/
    Borrower toBorrower(RequestBorrowerDto dto);/*dto => entity*/
    List<ResponseBorrowerDto> toResponseBorrowerDtoList(List<Borrower> list);
}
