package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookRepo extends JpaRepository<Book, Long> {
    List<Book> findAllByIsbn(String isbn);

    List<Book> findAllByIsbnAndIsAvailable(String isbn, boolean isAvailable);

    @Query(value = "SELECT * FROM book WHERE isbn LIKE ?1 OR title LIKE ?1 OR author LIKE ?1", nativeQuery = true)
    List<Book> searchBooks(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM book WHERE isbn LIKE ?1 OR title LIKE ?1 OR author LIKE ?1", nativeQuery = true)
    Long countBooks(String searchText);
}
