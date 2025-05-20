package com.devstack.healthCare.product.util.mapper;

import com.devstack.healthCare.product.dto.request.RequestBookDto;
import com.devstack.healthCare.product.dto.response.ResponseBookDto;
import com.devstack.healthCare.product.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    ResponseBookDto toResponseBookDto(Book book);
    /*@Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")*/
    Book toBook(RequestBookDto dto);
    List<ResponseBookDto> toResponseBookDtoList(List<Book> list);
}
