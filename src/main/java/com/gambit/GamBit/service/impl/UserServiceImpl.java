package com.gambit.GamBit.service.impl;

import com.gambit.GamBit.exception.UserNotAuthorizedException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.model.Role;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.repository.RoleRepository;
import com.gambit.GamBit.repository.UserRepository;
import com.gambit.GamBit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.gambit.GamBit.GamBitApplication.staticEntities;
import static com.gambit.GamBit.security.SecurityFunctions.*;
import static com.gambit.GamBit.security.SecurityFunctions.getUserNameByContext;
import static com.gambit.GamBit.service.common.Hashing.getHash;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registration(User user) throws UserAlreadyExistException {
        System.out.println("Registration user " + user.getName());
        if(userRepository.findByName(user.getName()) != null){
            System.out.println("User already exist " + user.getName());
            throw new UserAlreadyExistException("User with such name already exist");
        }
        // hash user password
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        // give user basic ROLE_USER
        Role role = roleRepository.findByName("ROLE_USER");
        if(role != null) user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("User with such id has not been found");
        }
        return user.get();
    }

    @Override
    public User getByName(String name) throws UserNotFoundException {
        User user = userRepository.findByName(name);
        if(user == null){
            throw new UserNotFoundException("User with such name has not been found");
        }

        return user;
    }

    @Override
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public User updateById(Long id, User updatedUser) throws UserNotFoundException, UserNotAuthorizedException {
        Optional<User> oldUser = userRepository.findById(id);
        if(!oldUser.isPresent()){
            throw new UserNotFoundException("User with such id has not been found");
        }
        User requestedUser = userRepository.findByName(getUserNameByContext());
        if(requestedUser == null ||
                (!getUserNameByContext().equals(oldUser.get().getName()) &&
                !hasRoleByContext(ROLE_ADMIN) )) {
            throw new UserNotAuthorizedException("You are not authorized to edit given profile");
        }
        updatedUser.setId(id);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        System.out.println("Adding role " + roleName + " to user " + userName);
        User user = userRepository.findByName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public void setVerification(Long id,
                                Boolean twoFactorEnabled,
                                String securityKey
    ) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("No user with such id");
        }
        User user = userOptional.get();
        user.setSecurityKey(getHash(securityKey));
        user.setTwoFactorEnabled(twoFactorEnabled);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        if(user.getTwoFactorEnabled() != null) {
            authorities.add(new SimpleGrantedAuthority("2FA"));
            staticEntities.notPassedVerificationUsers.add(user.getName());
            staticEntities.passedVerificationUsers.remove(user.getName());
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}
