package com.devstack.healthCare.product.repo;

import com.devstack.healthCare.product.entity.Borrower;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BorrowerRepo extends JpaRepository<Borrower,Long> {/*type and id*/
    public List<Borrower> findAllByName(String name);

    @Query(value = "SELECT * FROM borrower WHERE name LIKE ?1 OR address LIKE ?1", nativeQuery = true)
    public List<Borrower> searchBorrowers(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM borrower WHERE name LIKE ?1 OR address LIKE ?1", nativeQuery = true)
    public Long countBorrowers(String searchText);
}
