package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestBookLoanDto;
import com.devstack.healthCare.product.service.BookLoanService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loans")
public class BookLoanController {

    private final BookLoanService bookLoanService;

    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @PostMapping("/borrow")
    public ResponseEntity<StandardResponse> borrowBook(@RequestBody RequestBookLoanDto loanDto) {
        bookLoanService.borrowBook(loanDto);
        return new ResponseEntity<>(
                new StandardResponse(201, "Book borrowed successfully", null),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/return")
    public ResponseEntity<StandardResponse> returnBook(
            @RequestParam long bookId,
            @RequestParam long borrowerId) {
        bookLoanService.returnBook(bookId, borrowerId);
        return new ResponseEntity<>(
                new StandardResponse(200, "Book returned successfully", null),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/borrower", params = "borrowerId")
    public ResponseEntity<StandardResponse> getCurrentLoansForBorrower(@RequestParam long borrowerId) {
        return new ResponseEntity<>(
                new StandardResponse(200, "Current loans",
                        bookLoanService.getCurrentLoansForBorrower(borrowerId)),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<StandardResponse> getAllLoans(
            @RequestParam int page,
            @RequestParam int size) {
        return new ResponseEntity<>(
                new StandardResponse(200, "All loans",
                        bookLoanService.getAllLoans(page, size)),
                HttpStatus.OK
        );
    }
}
