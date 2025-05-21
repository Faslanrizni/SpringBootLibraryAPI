package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.Exception.EntryNotFoundException;
import com.devstack.healthCare.product.dto.request.RequestBorrowerDto;
import com.devstack.healthCare.product.dto.response.ResponseBorrowerDto;
import com.devstack.healthCare.product.dto.response.paginated.PaginatedBorrowerResponseDto;
import com.devstack.healthCare.product.entity.Borrower;
import com.devstack.healthCare.product.repo.BorrowerRepo;
import com.devstack.healthCare.product.service.BorrowerService;
import com.devstack.healthCare.product.util.mapper.BorrowerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerMapper borrowerMapper;
    private final BorrowerRepo borrowerRepo;


    @Autowired /*inject purpose*/
    public BorrowerServiceImpl(BorrowerMapper borrowerMapper, BorrowerRepo borrowerRepo) {
        this.borrowerMapper = borrowerMapper;
        this.borrowerRepo = borrowerRepo;
    }


    @Override
    public void createBorrower(RequestBorrowerDto dto) {
        UUID uuid = UUID.randomUUID();
        long docId = uuid.getMostSignificantBits();

        // Use the mapper instead of direct construction
        Borrower borrower = borrowerMapper.toBorrower(dto);
        borrower.setId(docId);

        // Or set them explicitly here to be safe
        if (borrower.getName() == null) borrower.setName(dto.getName());
        if (borrower.getAddress() == null) borrower.setAddress(dto.getAddress());
        if (borrower.getContact() == null) borrower.setContact(dto.getContact());

        borrowerRepo.save(borrower);
    }

    @Override
    public ResponseBorrowerDto getBorrower(long id) {
        Optional<Borrower> selectedBorrower = borrowerRepo.findById(id);
        /*Optional => to work with null values*/
        if (selectedBorrower.isEmpty()){
            throw new EntryNotFoundException("Borrower not found");
        }
        return borrowerMapper.toResponseBorrowerDto(selectedBorrower.get());
    }

    @Override
    public void deleteBorrower(long id) {
        Optional<Borrower> selectedBorrower = borrowerRepo.findById(id);
        /*Optional => to work with null values*/
        if (selectedBorrower.isEmpty()){
            throw new EntryNotFoundException("Borrower not found");
        }
        borrowerRepo.deleteById(selectedBorrower.get().getId());
    }

    @Override
    public List<ResponseBorrowerDto> findBorrowsByName(String name) {
        List<Borrower> allByName = borrowerRepo.findAllByName(name);
        return borrowerMapper.toResponseBorrowerDtoList(allByName);
    }

    @Override
    public void updateBorrower(long id, RequestBorrowerDto dto) {
        Optional<Borrower> selectedBorrower = borrowerRepo.findById(id);
        /*Optional => to work with null values*/
        if (selectedBorrower.isEmpty()){
            throw new EntryNotFoundException("Borrower not found");
        }
        Borrower borrower = selectedBorrower.get();
        borrower.setName(dto.getName());
        borrower.setAddress(dto.getAddress());
        borrower.setContact(dto.getContact());

        borrowerRepo.save(borrower);
    }

    @Override
    public PaginatedBorrowerResponseDto getAllBorrower(String searchText, int page, int size) {
        searchText="%"+searchText+"%";
        List<Borrower> borrowers = borrowerRepo.searchBorrowers(searchText, PageRequest.of(page, size));
        long borrowerCount = borrowerRepo.countBorrowers(searchText);
        List<ResponseBorrowerDto> dtos = borrowerMapper.toResponseBorrowerDtoList(borrowers);

        return new PaginatedBorrowerResponseDto(
                borrowerCount,
                dtos
        );
    }
}
