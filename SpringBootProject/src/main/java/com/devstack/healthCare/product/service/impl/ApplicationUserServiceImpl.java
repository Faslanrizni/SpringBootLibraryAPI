package com.devstack.healthCare.product.service.impl;

import com.devstack.healthCare.product.auth.ApplicationUser;
import com.devstack.healthCare.product.entity.User;
import com.devstack.healthCare.product.entity.UserRoleHasUser;
import com.devstack.healthCare.product.repo.UserRepo;
import com.devstack.healthCare.product.repo.UserRoleHasUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.devstack.healthCare.product.security.ApplicationUserRole.*;

@Service
public class ApplicationUserServiceImpl implements UserDetailsService {

    /*user filtering process done in here*/
    private final UserRepo userRepo;
    private final UserRoleHasUserRepo userRoleHasUserRepo;

    public ApplicationUserServiceImpl(UserRepo userRepo, UserRoleHasUserRepo userRoleHasUserRepo) {
        this.userRepo = userRepo;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User selcetedUser = userRepo.findByUserName(username);
        if (selcetedUser==null){
            throw new UsernameNotFoundException(String.format("userName is not found",username));

        }
        List<UserRoleHasUser> userRoles = userRoleHasUserRepo.findByUserId(selcetedUser.getId());
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();

        for (UserRoleHasUser userRole :userRoles ){
            if (userRole.getUserRole().getRoleName().equals("ADMIN")){
                grantedAuthorities.addAll(ADMIN.getSimpleGrantedAuthorities());

            }
            if (userRole.getUserRole().getRoleName().equals("BORROWER")){
                grantedAuthorities.addAll(BORROWER.getSimpleGrantedAuthorities());
            }

        }
        return new ApplicationUser(
                grantedAuthorities,
                selcetedUser.getPassword(),
                selcetedUser.getUsername(),
                selcetedUser.isAccountNonExpired(),
                selcetedUser.isAccountNonLocked(),
                selcetedUser.isCredentialsNonExpired(),
                selcetedUser.isEnabled()

        );


    }
}
