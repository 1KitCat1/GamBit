package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.UserAlreadyExistException;
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

    @PostMapping("/")
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

    @GetMapping("/")
    public ResponseEntity<String> getUserById(@RequestParam Long id){
        try{
            return ResponseEntity.ok("Server is online");
        } catch (Exception ex){
            return ResponseEntity.badRequest().body("Server is not responding");
        }
    }
}
