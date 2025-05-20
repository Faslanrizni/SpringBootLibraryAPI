package com.devstack.healthCare.product.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*@AllArgsConstructor
@NoArgsConstructor
@ToString*/
@Data /*to oya 3ma wenuwata*/
public class RequestBookDto {
    private String isbn;
    private String title;
    private String author;
}
