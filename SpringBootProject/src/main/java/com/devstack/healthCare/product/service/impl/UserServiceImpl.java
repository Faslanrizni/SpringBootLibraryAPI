package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.dto.request.RequestUserDto;
import com.devstack.healthCare.product.entity.User;
import com.devstack.healthCare.product.entity.UserRole;
import com.devstack.healthCare.product.entity.UserRoleHasUser;
import com.devstack.healthCare.product.repo.UserRepo;
import com.devstack.healthCare.product.repo.UserRoleHasUserRepo;
import com.devstack.healthCare.product.repo.UserRoleRepo;
import com.devstack.healthCare.product.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;

    private final UserRoleHasUserRepo userRoleHasUserRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, UserRoleRepo userRoleRepo, UserRoleHasUserRepo userRoleHasUserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(RequestUserDto userDto) {
        UserRole userRole;

        if (userDto.getId()==1){
            userRole = userRoleRepo.findUserRoleByName("ADMIN");
        }else{
            userRole = userRoleRepo.findUserRoleByName("BORROWER");
        }
        if (userRole == null){
            throw new RuntimeException("User role has not found");
        }
        User  user = new User(
                userDto.getId(),
                userDto.getFullname(),
                userDto.getUsername(),
                passwordEncoder.encode(userDto.getPassword()),
                true,
                true,
                true,
                true,
                null
        );

        UserRoleHasUser userData = new UserRoleHasUser(user,userRole);
        userRepo.save(user);
        userRoleHasUserRepo.save(userData);
    }

    @Override
    public boolean verifyUser(String type, String token) {
        return false;
    }
}
