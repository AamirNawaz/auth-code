package com.authapp.services;

import com.authapp.Customer;
import com.authapp.dto.CustomerRequestDto;

public interface AuthService {

    Customer creatCustomer(CustomerRequestDto customerRequestDto);
}
