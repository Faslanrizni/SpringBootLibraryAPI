package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestBorrowerDto;

import com.devstack.healthCare.product.service.BorrowerService;

import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController/*The class is marked as @RestController, meaning it is ready to handle web requests*/
@RequestMapping("api/v1/borrowers")

/* api => application program interface
doctores wala s akura ona*/

public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }
    /* constructor parameter */

    @PostMapping/*default data passing type is json we can chang it if we needed*/
    public ResponseEntity<StandardResponse> createBorrower(@RequestBody RequestBorrowerDto borrowerDto){
        borrowerService.createBorrower(borrowerDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"Borrower saved",borrowerDto.getName()),
                HttpStatus.CREATED
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> findBorrower(@PathVariable long id){
        return new ResponseEntity<>(
                new StandardResponse(200,"borrower data",borrowerService.getBorrower(id)),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteBorrower(@PathVariable long id){
        borrowerService.deleteBorrower(id);
        return new ResponseEntity<>(
                new StandardResponse(204,"deleted data",id),
                HttpStatus.NO_CONTENT
        );
    }
    @PutMapping(params = "id")
    public ResponseEntity<StandardResponse> updateBorrower(
            @RequestParam long id,
            @RequestBody RequestBorrowerDto borrowerDto){
        borrowerService.updateBorrower(id,borrowerDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"update data",borrowerDto.getName()),
                HttpStatus.CREATED
        );
    }
    @GetMapping(value = "/list",params = {"searchText","page","size"})
    public ResponseEntity<StandardResponse> findAllBorrower(
            @RequestParam String searchText,
            @RequestParam int page,
            @RequestParam int size
    ){

        return new ResponseEntity<>(
                new StandardResponse(200,"data list",borrowerService.getAllBorrower(
                        searchText, page, size)),
                HttpStatus.OK
        );
    }

}
