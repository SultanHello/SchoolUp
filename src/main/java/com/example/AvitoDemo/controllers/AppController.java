package com.example.AvitoDemo.controllers;

import com.example.AvitoDemo.Model.Login;
import com.example.AvitoDemo.Model.Register;
import com.example.AvitoDemo.service.AppService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AppController {

    private final AppService appService;

    @GetMapping("/hello")
    public String hello(){
        return "hello from : "+appService.getName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/reg/student")
    public String regStudent( @RequestBody Register student){
        System.out.println(12);
        return appService.registrationStudent(student);
    }
    @PostMapping("/reg/teacher")
    public String regTeacher( @RequestBody Register teacher){
        return appService.registrationTeacher(teacher);
    }
    @PostMapping("/login")
    public String login(@RequestBody Login login) throws BadRequestException {
        return appService.auth(login);
    }






}
