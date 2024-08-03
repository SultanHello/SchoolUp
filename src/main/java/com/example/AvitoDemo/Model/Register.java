package com.example.AvitoDemo.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String name;
    private String surname;
    private String email;
    private String password;

}
