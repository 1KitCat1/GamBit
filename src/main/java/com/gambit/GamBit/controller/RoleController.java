package com.gambit.GamBit.controller;

import com.gambit.GamBit.model.Role;
import com.gambit.GamBit.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.
                    ok("Role " + role.getName() + " has been successfully added");
        } catch(Exception ex){
            return ResponseEntity.badRequest().
                    body("Error occurred during adding role " + role.getName());
        }
    }

    @GetMapping(ACCESS_ADMIN + ROLES + "/getAll")
    public ResponseEntity<List<Role>> getAllRoles(){
        try{
            return ResponseEntity.ok(roleService.getAll());
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping(ACCESS_ADMIN + ROLES + "/delete")
    public ResponseEntity<String> deleteRole(@RequestParam Long id){
        try{
            roleService.deleteRole(id);
            return ResponseEntity.ok("Role has been successfully deleted");
        } catch (Exception ex){
            return ResponseEntity.badRequest().
                    body("Error occurred while deleting the role");
        }
    }

    @PutMapping(ACCESS_ADMIN + ROLES + "/update")
    public ResponseEntity<String> updateRole(@RequestParam Long id,
                                             @RequestBody Role updatedRole)
    {
        try{
            roleService.updateRole(id, updatedRole);
            return ResponseEntity.ok("Role has been successfully updated");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().
                    body("Error occurred while updating the role");
        }
    }
}
