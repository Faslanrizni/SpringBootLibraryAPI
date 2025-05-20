package com.devstack.healthCare.product.service;

import com.devstack.healthCare.product.dto.request.RequestBookDto;
import com.devstack.healthCare.product.dto.response.ResponseBookDto;
import com.devstack.healthCare.product.dto.response.paginated.PaginatedBookResponseDto;

import java.util.List;

public interface BookService {
    public void createBook(RequestBookDto dto);
    ResponseBookDto getBook(long id);
    public void deleteBook(long id);
    public List<ResponseBookDto> findBooksByIsbn(String isbn);
    public void updateBook(long id, RequestBookDto dto);
    public PaginatedBookResponseDto getAllBooks(String searchText, int page, int size);
}

