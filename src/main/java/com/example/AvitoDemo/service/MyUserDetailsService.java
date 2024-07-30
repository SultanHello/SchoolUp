package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Model.Student;
import com.example.AvitoDemo.Model.Teacher;
import com.example.AvitoDemo.repasitory.StudentRepository;
import com.example.AvitoDemo.repasitory.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final TeacherRepository teacherRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByEmail(username);

        if(teacher!=null){
            return teacher;
        }
        Student student  = studentRepository.findByEmail(username);
        if(student!=null){
            return student;
        }
        throw  new UsernameNotFoundException("User not found with username : "+ username);

    }
}
