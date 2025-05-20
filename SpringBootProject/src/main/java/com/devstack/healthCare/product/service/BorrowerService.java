package com.devstack.healthCare.product.service;

import com.devstack.healthCare.product.dto.request.RequestBorrowerDto;
import com.devstack.healthCare.product.dto.response.ResponseBorrowerDto;
;
import com.devstack.healthCare.product.dto.response.paginated.PaginatedBorrowerResponseDto;


import java.util.List;

public interface BorrowerService  {

        public void createBorrower(RequestBorrowerDto dto);
        public ResponseBorrowerDto getBorrower(long id);
        public void deleteBorrower(long id);
        public List<ResponseBorrowerDto> findBorrowsByName(String name);
        public void updateBorrower(long id, RequestBorrowerDto dto);
        public PaginatedBorrowerResponseDto getAllBorrower(String searchText, int page, int size);

}

