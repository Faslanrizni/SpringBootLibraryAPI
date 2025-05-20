package com.devstack.healthCare.product.api;

import com.devstack.healthCare.product.dto.request.RequestUserDto;
import com.devstack.healthCare.product.service.UserService;
import com.devstack.healthCare.product.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService ;

    @PostMapping("/visitor/signup")
    public ResponseEntity<StandardResponse> createBorrower(@RequestBody RequestUserDto userDto){
        userService.signup(userDto);
        return new ResponseEntity<>(
                new StandardResponse(201,"user was saved!",
                        userDto.getFullname()),
                HttpStatus.CREATED
        );
    }
}
