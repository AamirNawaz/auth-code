package com.authapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerDashboard {

    @GetMapping("/dashboard")
    public String dashboard(){
        return "Customer dashboard api";
    }

}
