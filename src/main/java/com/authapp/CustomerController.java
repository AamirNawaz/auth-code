package com.authapp;

import com.authapp.dto.CustomerRequestDto;
import com.authapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private final AuthService authService;

    public CustomerController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public Customer signupCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return authService.creatCustomer(customerRequestDto);

    }


}
