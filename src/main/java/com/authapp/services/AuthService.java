package com.authapp.services;

import com.authapp.model.Customer;
import com.authapp.dto.CustomerRequestDto;

public interface AuthService {

    Customer creatCustomer(CustomerRequestDto customerRequestDto);
}
