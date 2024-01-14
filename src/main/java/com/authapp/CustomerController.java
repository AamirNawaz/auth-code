package com.authapp;

import com.authapp.dto.CustomerLoginRequest;
import com.authapp.dto.CustomerLoginResponse;
import com.authapp.dto.CustomerRequestDto;
import com.authapp.jwtUtil.JwtUtil;
import com.authapp.services.CustomerServiceImpl;
import com.authapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;
    public CustomerController(AuthService authService, AuthenticationManager authenticationManager, CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public Customer signupCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return authService.creatCustomer(customerRequestDto);

    }

    @PostMapping("/login")
    public ResponseEntity<CustomerLoginResponse> loginCustomer(@RequestBody CustomerLoginRequest customerLoginRequest){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customerLoginRequest.getEmail(),customerLoginRequest.getPassword())
            );

        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try{
            userDetails = customerService.loadUserByUsername(customerLoginRequest.getEmail());
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        String token = jwtUtil.generateToken(userDetails.getUsername());
       return ResponseEntity.ok().body(new CustomerLoginResponse(token));
    }


}
