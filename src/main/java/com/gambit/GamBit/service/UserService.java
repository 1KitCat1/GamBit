package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.User;

import java.util.List;

public interface UserService {

    public User registration(User user) throws UserAlreadyExistException;
    public User getById(Long id) throws UserNotFoundException;
    public User getUserByName(String name) throws  UserNotFoundException;
    public User deleteUserById(Long id) throws  UserNotFoundException;
//    public List<User> getAll();
}
