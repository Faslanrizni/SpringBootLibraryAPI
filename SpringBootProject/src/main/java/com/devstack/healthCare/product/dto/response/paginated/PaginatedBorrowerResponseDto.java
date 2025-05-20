package com.devstack.healthCare.product.dto.response.paginated;

import com.devstack.healthCare.product.dto.response.ResponseBorrowerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginatedBorrowerResponseDto {
    private long count;
    private List<ResponseBorrowerDto> dataList;




}
