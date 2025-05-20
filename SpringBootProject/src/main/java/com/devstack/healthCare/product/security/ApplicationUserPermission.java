package com.devstack.healthCare.product.security;

public enum ApplicationUserPermission {
    BORROWER_READ("borrower:read"),
    BORROWER_WRITE("borrower:write");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
