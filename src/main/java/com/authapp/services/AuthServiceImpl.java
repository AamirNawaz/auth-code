package com.authapp.services;

import com.authapp.model.Customer;
import com.authapp.repository.CustomerRepository;
import com.authapp.dto.CustomerRequestDto;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer creatCustomer(CustomerRequestDto customerRequestDto) {
            Customer customer = new Customer();
            BeanUtils.copyProperties(customerRequestDto, customer);
            String hashPassword = passwordEncoder.encode(customerRequestDto.getPassword());
            customer.setPassword(hashPassword);
           return customerRepository.save(customer);
           }
}
