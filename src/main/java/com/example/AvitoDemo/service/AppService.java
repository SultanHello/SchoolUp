package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Model.*;
import com.example.AvitoDemo.repasitory.StudentRepository;
import com.example.AvitoDemo.repasitory.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.cfg.DefaultCacheProvider.builder;

@Service
@AllArgsConstructor

public class AppService {
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final TeacherRepository teacherRepository;
    @Autowired
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager manager;
    public String getName(String email){
        Student student = studentRepository.findByEmail(email);
        if(student!=null){
            return student.getName();
        }
        Teacher teacher = teacherRepository.findByEmail(email);
        if (teacher!=null){
            return teacher.getName();
        }
        throw  new UsernameNotFoundException("not found by email : "+email);



    }

    public String registrationStudent(Register registerUser){
        return jwtService.generateToken(studentRepository.save(userToStudent(registerUser)));
    }
    public String registrationTeacher(Register registerUser){
        return jwtService.generateToken(studentRepository.save(userToStudent(registerUser)));
    }

    public String auth(Login login){
        manager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
        Student student = studentRepository.findByEmail(login.getEmail());
        Teacher teacher = teacherRepository.findByEmail(login.getEmail());
        if(student!=null){
            return jwtService.generateToken(student);

        }else if(teacher!=null){
            return jwtService.generateToken(teacher);

        }
        return null;




    }


    private Student userToStudent(Register user){
        return Student
                .builder()
                .name(user.getName())
                .role(RoleStudent.DEFAULT)
                .surname(user.getSurname())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail()).build();
    }




}
