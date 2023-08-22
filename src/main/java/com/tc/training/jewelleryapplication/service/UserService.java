package com.tc.training.jewelleryapplication.service;

import com.tc.training.jewelleryapplication.exception.UserException;
import com.tc.training.jewelleryapplication.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;


    public User findUserProfileByJwt(String jwt) throws UserException;

}
