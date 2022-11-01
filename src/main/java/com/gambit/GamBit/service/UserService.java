package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.User;

public interface UserService {

    public User registration(User user) throws UserAlreadyExistException;
    public User getById(Long id) throws UserNotFoundException;
}
