package com.devstack.healthCare.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowRecord {
    @Id
    private long id;

    // Fix: Make sure to use your own Book entity, not java.awt.print.Book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private com.devstack.healthCare.product.entity.Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private boolean returned;
}
