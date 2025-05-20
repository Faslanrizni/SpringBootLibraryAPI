package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestBookDto;
import com.devstack.healthCare.product.dto.request.RequestBorrowerDto;
import com.devstack.healthCare.product.service.BookService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<StandardResponse> createBook(@RequestBody RequestBookDto bookDto) {
        bookService.createBook(bookDto);
        return new ResponseEntity<>(
                new StandardResponse(201, "Book saved", bookDto.getTitle()),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> findBook(@PathVariable long id) {
        return new ResponseEntity<>(
                new StandardResponse(200, "Book data", bookService.getBook(id)),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(
                new StandardResponse(204, "Deleted data", id),
                HttpStatus.NO_CONTENT
        );
    }

    @PutMapping(params = "id")
    public ResponseEntity<StandardResponse> updateBook(
            @RequestParam long id,
            @RequestBody RequestBookDto bookDto) {
        bookService.updateBook(id, bookDto);
        return new ResponseEntity<>(
                new StandardResponse(201, "Update data", bookDto.getTitle()),
                HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/list", params = {"searchText", "page", "size"})
    public ResponseEntity<StandardResponse> findAllBooks(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return new ResponseEntity<>(
                new StandardResponse(200, "Data list", bookService.getAllBooks(
                        searchText, page, size)),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/isbn", params = "isbn")
    public ResponseEntity<StandardResponse> findBooksByIsbn(@RequestParam String isbn) {
        return new ResponseEntity<>(
                new StandardResponse(200, "Books by ISBN", bookService.findBooksByIsbn(isbn)),
                HttpStatus.OK
        );
    }
}
