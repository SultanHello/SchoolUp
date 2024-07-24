package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Model.Register;
import com.example.AvitoDemo.Model.RoleStudent;
import com.example.AvitoDemo.Model.Student;
import com.example.AvitoDemo.Model.User;
import com.example.AvitoDemo.repasitory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class AppService {
    @Autowired
    private final UserRepository userRepository;
    public String registration(Register registerUser){
         User user  = User.builder()
                .email(registerUser.getEmail())
                .name(registerUser.getName())
                .role(RoleStudent.DEFAULT)
                 .email(registerUser.getEmail())
                 .password(registerUser.getPassword())
                .build();
        userRepository.

    }

}
