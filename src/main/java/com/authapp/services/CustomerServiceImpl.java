package com.authapp.services;

import com.authapp.model.Customer;
import com.authapp.model.Role;
import com.authapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found!"));

        Set<Role> roles = customer.getRoles();
        System.out.println("Roles for user " + email + ": " + roles);

        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
