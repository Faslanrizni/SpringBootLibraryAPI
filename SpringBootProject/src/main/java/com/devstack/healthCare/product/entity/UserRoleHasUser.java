package com.devstack.healthCare.product.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class UserRoleHasUser {
    @EmbeddedId
    private UserRoleHasUserKey id= new UserRoleHasUserKey();

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @MapsId("userRole")
    @JoinColumn(name = "role_id",nullable = false)
    private UserRole userRole;

    public UserRoleHasUser(User user, UserRole userRole) {
        this.user = user;
        this.userRole = userRole;
    }
}
