package com.example.AvitoDemo.Model;

import lombok.Data;

@Data
public class Register {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;

}