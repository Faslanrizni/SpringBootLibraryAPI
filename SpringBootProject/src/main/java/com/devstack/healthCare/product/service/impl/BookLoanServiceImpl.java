package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.Exception.EntryNotFoundException;
import com.devstack.healthCare.product.dto.request.RequestBookLoanDto;
import com.devstack.healthCare.product.dto.response.ResponseBookLoanDto;
import com.devstack.healthCare.product.entity.Book;
import com.devstack.healthCare.product.entity.BookLoan;
import com.devstack.healthCare.product.entity.Borrower;
import com.devstack.healthCare.product.repo.BookLoanRepo;
import com.devstack.healthCare.product.repo.BookRepo;
import com.devstack.healthCare.product.repo.BorrowerRepo;
import com.devstack.healthCare.product.service.BookLoanService;
import com.devstack.healthCare.product.util.mapper.BookLoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookLoanServiceImpl implements BookLoanService {
    private final BookLoanMapper bookLoanMapper;
    private final BookLoanRepo bookLoanRepo;
    private final BookRepo bookRepo;
    private final BorrowerRepo borrowerRepo;

    @Autowired
    public BookLoanServiceImpl(BookLoanMapper bookLoanMapper, BookLoanRepo bookLoanRepo,
                               BookRepo bookRepo, BorrowerRepo borrowerRepo) {
        this.bookLoanMapper = bookLoanMapper;
        this.bookLoanRepo = bookLoanRepo;
        this.bookRepo = bookRepo;
        this.borrowerRepo = borrowerRepo;
    }

    @Override
    @Transactional
    public void borrowBook(RequestBookLoanDto dto) {
        Optional<Book> bookOptional = bookRepo.findById(dto.getBookId());
        Optional<Borrower> borrowerOptional = borrowerRepo.findById(dto.getBorrowerId());

        if (bookOptional.isEmpty()) {
            throw new EntryNotFoundException("Book not found");
        }

        if (borrowerOptional.isEmpty()) {
            throw new EntryNotFoundException("Borrower not found");
        }

        Book book = bookOptional.get();
        Borrower borrower = borrowerOptional.get();

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        // Create a new Book borrow
        BookLoan bookLoan = new BookLoan();
        UUID uuid = UUID.randomUUID();
        bookLoan.setId(uuid.getMostSignificantBits());
        bookLoan.setBook(book);
        bookLoan.setBorrower(borrower);
        bookLoan.setBorrowDate(LocalDateTime.now());
        bookLoan.setReturned(false);

        // Update book availability
        book.setAvailable(false);

        // Save both entities
        bookRepo.save(book);
        bookLoanRepo.save(bookLoan);
    }

    @Override
    @Transactional
    public void returnBook(long bookId, long borrowerId) {
        Optional<Book> bookOptional = bookRepo.findById(bookId);
        Optional<Borrower> borrowerOptional = borrowerRepo.findById(borrowerId);

        if (bookOptional.isEmpty()) {
            throw new EntryNotFoundException("Book not found");
        }

        if (borrowerOptional.isEmpty()) {
            throw new EntryNotFoundException("Borrower not found");
        }

        Book book = bookOptional.get();

        // Find the active loan for this book
        BookLoan bookLoan = bookLoanRepo.findByBookAndReturnedFalse(book);

        if (bookLoan == null) {
            throw new RuntimeException("No active loan found for this book");
        }

        if (bookLoan.getBorrower().getId() != borrowerId) {
            throw new RuntimeException("This book was borrowed by a different borrower");
        }

        // Update loan status
        bookLoan.setReturned(true);
        bookLoan.setReturnDate(LocalDateTime.now());


        book.setAvailable(true);

        // Save both entities
        bookRepo.save(book);
        bookLoanRepo.save(bookLoan);
    }

    @Override
    public List<ResponseBookLoanDto> getCurrentLoansForBorrower(long borrowerId) {
        Optional<Borrower> borrowerOptional = borrowerRepo.findById(borrowerId);

        if (borrowerOptional.isEmpty()) {
            throw new EntryNotFoundException("Borrower not found");
        }

        List<BookLoan> loans = bookLoanRepo.findAllByBorrowerAndReturnedFalse(borrowerOptional.get());
        return bookLoanMapper.toResponseBookLoanDtoList(loans);
    }

    @Override
    public List<ResponseBookLoanDto> getAllLoans(int page, int size) {
        return bookLoanMapper.toResponseBookLoanDtoList(
                bookLoanRepo.findAll(PageRequest.of(page, size)).getContent()
        );
    }
}
