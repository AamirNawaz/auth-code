package com.authapp.services;

import com.authapp.dto.CustomerRequestDto;
import com.authapp.model.Customer;
import com.authapp.model.Role;
import com.authapp.repository.CustomerRepository;
import com.authapp.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer creatCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        customer.setName(customerRequestDto.getName());
        customer.setEmail(customerRequestDto.getEmail());
        String hashPassword = passwordEncoder.encode(customerRequestDto.getPassword());
        customer.setPassword(hashPassword);


        Set<Role> roles = new HashSet<>();
        for (String roleName : customerRequestDto.getRoles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new EntityNotFoundException("Role with name " + roleName + " not found"));
            roles.add(role);
        }

        customer.setRoles(roles);

        return customerRepository.save(customer);
    }
}
