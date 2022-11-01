package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.repository.UserRepo;
import com.gambit.GamBit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User registration(User user) throws UserAlreadyExistException {
        if(userRepo.findByName(user.getName()) != null){
            throw new UserAlreadyExistException("User with such name already exist");
        }
        return userRepo.save(user);
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepo.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with such id has not been found");
        }
        return user.get();
    }
}
