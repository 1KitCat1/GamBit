package com.gambit.GamBit.controller;

import com.gambit.GamBit.exception.UserNotFoundException;
import com.gambit.GamBit.model.DecentralizedNetwork;
import com.gambit.GamBit.model.Role;
import com.gambit.GamBit.model.User;
import com.gambit.GamBit.service.DecentralizedNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_ADMIN;
import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_USER;

@RestController
@RequiredArgsConstructor
public class DecentralizedNetworkController {
    private final DecentralizedNetworkService decentralizedNetworkService;
    private final String NETWORKS = "/networks";

    @PostMapping(ACCESS_ADMIN + NETWORKS + "/add")
    public ResponseEntity<String> addRole(@RequestBody DecentralizedNetwork network){
        System.out.println(network);
        try {
            decentralizedNetworkService.addNetwork(network);
            return ResponseEntity.ok("Role " + network.getName() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding role " + network.getName());
        }
    }

    @GetMapping(ACCESS_USER + NETWORKS + "/getAll")
    public ResponseEntity<List<DecentralizedNetwork>> getAllNetworks(){
        try{
            return ResponseEntity.ok(decentralizedNetworkService.getAll());
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(ACCESS_USER + NETWORKS + "/getById")
    public ResponseEntity<DecentralizedNetwork> getUserById(@RequestParam Long id){
        try{
            return ResponseEntity.ok(decentralizedNetworkService.getById(id));
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_ADMIN + NETWORKS + "/delete")
    public ResponseEntity<String> deleteUser(@RequestParam long id) {
        try {
            decentralizedNetworkService.deleteById(id);
            return ResponseEntity.ok("User with id " + id + " has been deleted.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(ACCESS_ADMIN + NETWORKS + "/update")
    public ResponseEntity<String> updateUser(@RequestParam long id, @RequestBody User updatedUser){
        try {
            decentralizedNetworkService.updateById(id, updatedUser);
            return ResponseEntity.ok("User with id " + id + " has been updated.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error occurred during user update");
        }
    }
}
