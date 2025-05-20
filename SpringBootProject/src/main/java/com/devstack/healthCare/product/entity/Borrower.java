package com.devstack.healthCare.product.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity/*whenever Hibernate sees @Entity, it will create a table using the name of our class as table name*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Borrower {
    @Id
    private long id;
    private String name;
    private String address;
    private String contact;

}
