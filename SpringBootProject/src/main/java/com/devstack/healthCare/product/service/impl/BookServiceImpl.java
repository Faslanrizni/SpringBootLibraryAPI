package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.Exception.EntryNotFoundException;
import com.devstack.healthCare.product.dto.request.RequestBookDto;
import com.devstack.healthCare.product.dto.response.ResponseBookDto;
import com.devstack.healthCare.product.dto.response.paginated.PaginatedBookResponseDto;
import com.devstack.healthCare.product.entity.Book;
import com.devstack.healthCare.product.repo.BookRepo;
import com.devstack.healthCare.product.service.BookService;
import com.devstack.healthCare.product.util.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;
    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookMapper bookMapper, BookRepo bookRepo) {
        this.bookMapper = bookMapper;
        this.bookRepo = bookRepo;
    }
/* @Override
    public void createBorrower(RequestBorrowerDto dto) {
        UUID uuid = UUID.randomUUID();
        long docId = uuid.getMostSignificantBits();

        // Use the mapper instead of direct construction
        Borrower borrower = borrowerMapper.toDoctor(dto);
        borrower.setId(docId);
        borrowerRepo.save(borrower);
    }*/
    @Override
    public void createBook(RequestBookDto dto) {
        UUID uuid = UUID.randomUUID();
        long bookId = uuid.getMostSignificantBits();

        Book book = bookMapper.toBook(dto);
        book.setId(bookId);
        book.setAvailable(true);
        if (book.getIsbn() == null) book.setIsbn(dto.getIsbn());
        if (book.getAuthor() == null) book.setAuthor(dto.getAuthor());
        if (book.getTitle() == null) book.setTitle(dto.getTitle());
        System.out.println("Book to save: " + book);
        bookRepo.save(book);
    }

    @Override
    public ResponseBookDto getBook(long id) {
        Optional<Book> selectedBook = bookRepo.findById(id);
        System.out.println();
        if (selectedBook.isEmpty()) {
            throw new EntryNotFoundException("Book not found");
        }
        return bookMapper.toResponseBookDto(selectedBook.get());
    }

    @Override
    public void deleteBook(long id) {
        Optional<Book> selectedBook = bookRepo.findById(id);
        if (selectedBook.isEmpty()) {
            throw new EntryNotFoundException("Book not found");
        }
        bookRepo.deleteById(selectedBook.get().getId());
    }

    @Override
    public List<ResponseBookDto> findBooksByIsbn(String isbn) {
        List<Book> allByIsbn = bookRepo.findAllByIsbn(isbn);
        return bookMapper.toResponseBookDtoList(allByIsbn);
    }

    @Override
    public void updateBook(long id, RequestBookDto dto) {
        Optional<Book> selectedBook = bookRepo.findById(id);
        if (selectedBook.isEmpty()) {
            throw new EntryNotFoundException("Book not found");
        }
        Book book = selectedBook.get();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());

        bookRepo.save(book);
    }

    @Override
    public PaginatedBookResponseDto getAllBooks(String searchText, int page, int size) {
        searchText = "%" + searchText + "%";
        List<Book> books = bookRepo.searchBooks(searchText, PageRequest.of(page, size));
        long bookCount = bookRepo.countBooks(searchText);
        List<ResponseBookDto> dtos = bookMapper.toResponseBookDtoList(books);

        return new PaginatedBookResponseDto(
                bookCount,
                dtos
        );
    }
}
