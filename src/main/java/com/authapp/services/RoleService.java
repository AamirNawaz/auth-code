package com.authapp.services;

import com.authapp.dto.RoleRequest;
import com.authapp.model.Role;
import com.authapp.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role create(RoleRequest roleRequest){
        Role newRole = new Role();
        newRole.setName(roleRequest.getName());
        return roleRepository.save(newRole);
    }

    public List<Role> get() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("Record not found!"));
    }

    public String Delete(Long id) {
        roleRepository.deleteById(id);
        return "Record No:"+id+" deleted successfully!";
    }

}
