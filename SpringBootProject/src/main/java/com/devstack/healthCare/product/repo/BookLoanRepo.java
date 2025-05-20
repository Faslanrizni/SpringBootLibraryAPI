package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.Book;
import com.devstack.healthCare.product.entity.BookLoan;
import com.devstack.healthCare.product.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookLoanRepo extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findAllByBorrowerAndReturnedFalse(Borrower borrower);
    List<BookLoan> findAllByBookAndReturnedFalse(Book book);
    BookLoan findByBookAndReturnedFalse(Book book);
}
