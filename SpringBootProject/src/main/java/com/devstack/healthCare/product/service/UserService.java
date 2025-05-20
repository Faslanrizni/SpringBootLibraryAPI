package com.devstack.healthCare.product.service;

import com.devstack.healthCare.product.dto.request.RequestUserDto;

public interface UserService {
    public void signup(RequestUserDto userDto);
    public boolean verifyUser(String type, String token);
}
