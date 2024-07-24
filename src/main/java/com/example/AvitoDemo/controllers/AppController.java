package com.example.AvitoDemo.controllers;

import com.example.AvitoDemo.Model.Register;
import com.example.AvitoDemo.service.AppService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppController {

    private final AppService appService;
    @GetMapping("/hello")
    public String hello(){
        return "hello from : "+
    }

    @PostMapping("/reg")
    public String reg(Register user){
        appService.registration(user);
    }

}
