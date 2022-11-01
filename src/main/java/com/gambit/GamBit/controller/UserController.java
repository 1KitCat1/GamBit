package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.repository.UserRepo;
import com.gambit.GamBit.service.UserService;
import com.gambit.GamBit.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registration(@RequestBody User user){
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
    public ResponseEntity<String> getUsers(){
        try{
            return ResponseEntity.ok("Server is online");
        } catch (Exception ex){
            return ResponseEntity.badRequest().body("Server is not responding");
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

    @GetMapping("/getByUsername")
    public ResponseEntity<User> getUserByUsername(@RequestParam String name) {
        try {
            return ResponseEntity.ok(userService.getUserByName(name));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User id" + id + " has been deleted.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
