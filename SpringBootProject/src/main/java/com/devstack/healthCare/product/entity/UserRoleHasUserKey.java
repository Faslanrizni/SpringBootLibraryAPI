package com.devstack.healthCare.product.entity;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserRoleHasUserKey implements Serializable {
    /*
    we implement this from Serializable because of markerInterface (to get behaviors to this class)
    and now we can pass this as object to another class

    * */
    @Column(name = "user_id")
    private long user;

    @Column(name = "role_id" )
    private long userRole;
}

/* to map many to many
* to map user and user role
* */
