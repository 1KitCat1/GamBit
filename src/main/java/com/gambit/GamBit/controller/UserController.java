package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody User user){
        System.out.println(user);
        try {
            userService.registration(user);
            return ResponseEntity.ok("New user has been successfully added");
        } catch (UserAlreadyExistException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during registration");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<User> getUserById(@RequestParam Long id){
        try{
            return ResponseEntity.ok(userService.getById(id));
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getByName")
    public ResponseEntity<User> getUserByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(userService.getByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/update/")
    public ResponseEntity<String> updateUser(@RequestParam long id, @RequestBody User updatedUser){
        try {
            userService.updateById(id, updatedUser);
            return ResponseEntity.ok("User with id " + id + " has been updated.");
        } catch (UserNotFoundException ex) {
            return ResponseEntity.badRequest().body("User with id " + id + " has not been found.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during user update");
        }
    }

    @PostMapping("/addRole")
    public ResponseEntity<String> addRoleToUser(@RequestParam String userName, @RequestParam String roleName){
        try {
            userService.addRoleToUser(userName, roleName);
            return ResponseEntity.ok("User " + userName + " now has role " + roleName);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during adding role to user");
        }
    }
}
