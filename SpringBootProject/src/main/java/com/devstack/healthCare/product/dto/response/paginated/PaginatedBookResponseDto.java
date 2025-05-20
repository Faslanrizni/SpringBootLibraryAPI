package com.devstack.healthCare.product.dto.response.paginated;

import com.devstack.healthCare.product.dto.response.ResponseBookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedBookResponseDto {
    private long count;
    private List<ResponseBookDto> data;
}
