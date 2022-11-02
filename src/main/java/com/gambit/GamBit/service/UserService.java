package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.User;

import java.util.List;

public interface UserService {

    public User registration(User user) throws UserAlreadyExistException;
    public User getById(Long id) throws UserNotFoundException;
    public User getByName(String name) throws  UserNotFoundException;
    public void deleteById(Long id);
    public User updateById(Long id, User updatedUser) throws UserNotFoundException;
    public List<User> getAll();
//    public List<User> getAll();
}
