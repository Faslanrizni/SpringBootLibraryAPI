package com.devstack.healthCare.product.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    BORROWER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet());
    private final Set<ApplicationUserPermission> permission;

    ApplicationUserRole(Set<ApplicationUserPermission> permission) {
        this.permission = permission;
    }

    public Set<ApplicationUserPermission> getPermissions(){
        return permission;


    }
    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions= getPermissions()
                .stream().map(permission->new
                        SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return permissions;
    }
}
