package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.Role;
import com.gambit.GamBit.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        try {
            roleService.addRole(role);
            return ResponseEntity.ok("Role " + role.getName() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding role " + role.getName());
        }
    }
}
