package com.authapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    private String name;
    private String email;
    private String password;
    private Set<String> roles;
}
