package com.authapp.services;

import com.authapp.dto.CustomerResponseDto;
import com.authapp.model.Customer;
import com.authapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Email not found!"));
        return new User(customer.getEmail(), customer.getPassword(), Collections.emptyList());

    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
