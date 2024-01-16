package com.authapp.controller;

import com.authapp.dto.RoleRequest;
import com.authapp.model.Role;
import com.authapp.services.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> get(){
        return roleService.get();
    }
    @PostMapping("/create")
    public Role create(@RequestBody RoleRequest roleRequest){
        return roleService.create(roleRequest);
    }

    @GetMapping("/get/{id}")
    public Role getRole(@PathVariable Long id){
        return roleService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id){
        return roleService.Delete(id);
    }

}
