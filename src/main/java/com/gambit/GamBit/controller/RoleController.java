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

import static com.gambit.GamBit.controller.AccessRolesController.ACCESS_ADMIN;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    private final String ROLES = "/roles";
    @PostMapping(ACCESS_ADMIN + ROLES + "/add")
    public ResponseEntity<String> addRole(@RequestBody Role role){
        System.out.println(role);
        try {
            roleService.addRole(role);
            return ResponseEntity.ok("Role " + role.getName() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().body("Error occurred during adding role " + role.getName());
        }
    }
}
