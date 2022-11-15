package com.gambit.GamBit.service;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.User;

import java.util.List;

public interface UserService {

    User registration(User user) throws UserAlreadyExistException;
    User getById(Long id) throws UserNotFoundException;
    User getByName(String name) throws  UserNotFoundException;
    void deleteById(Long id);
    User updateById(Long id, User updatedUser) throws UserNotFoundException;
    List<User> getAll();
    void addRoleToUser(String userName, String roleName);
}
