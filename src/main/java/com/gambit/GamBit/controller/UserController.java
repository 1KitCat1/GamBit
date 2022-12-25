package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.UserAlreadyExistException;
import com.gambit.GamBit.exception.UserNotAuthorizedException;
import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gambit.GamBit.controller.AccessRolesController.*;

@RestController
//@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    private final String USERS = "/users";

    @PostMapping(ACCESS_VISITOR + USERS + "/register")
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

    @GetMapping(ACCESS_ADMIN + USERS + "/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_ADMIN + USERS + "/getById")
    public ResponseEntity<User> getUserById(@RequestParam Long id){
        try{
            return ResponseEntity.ok(userService.getById(id));
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_ADMIN + USERS + "/getByName")
    public ResponseEntity<User> getUserByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(userService.getByName(name));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_ADMIN + USERS + "/delete")
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_USER + USERS + "/update")
    public ResponseEntity<String> updateUser(@RequestParam long id, @RequestBody User updatedUser){
        try {
            userService.updateById(id, updatedUser);
            return ResponseEntity.ok("User with id " + id + " has been updated.");
        } catch (UserNotFoundException | UserNotAuthorizedException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during user update");
        }
    }

    @PostMapping(ACCESS_ADMIN + USERS + "/addRole")
    public ResponseEntity<String> addRoleToUser(@RequestParam String userName, @RequestParam String roleName){
        try {
            userService.addRoleToUser(userName, roleName);
            return ResponseEntity.ok("User " + userName + " now has role " + roleName);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during adding role to user");
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> testRequest(){
        return ResponseEntity.ok("Okshdsdfj");
    }
    @PostMapping("/testPost")
    public ResponseEntity<String> testPostRequest(){
        return ResponseEntity.ok("OkshdsdfjPost");
    }

    @PostMapping(ACCESS_USER + USERS + "/set2FA")
    public ResponseEntity<String> setVerification(@RequestBody TwoFactorVerificationInput input
    ){
        try {
            userService.setVerification(input.userId,
                                        input.twoFactorEnabled,
                                        input.verificationKey);
            return ResponseEntity.ok("Two factor authentication settings updated");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during updating verification parameters");
        }
    }

    @Data
    private static class TwoFactorVerificationInput {
        private Long userId;
        private Boolean twoFactorEnabled;
        private String verificationKey;
    }
}
