package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Exeptions.StudentNotFoundExceptions;
import com.example.AvitoDemo.Model.*;
import com.example.AvitoDemo.repasitory.StudentRepository;
import com.example.AvitoDemo.repasitory.TeacherRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String registrationStudent(Register registerUser) {
        Student student = userToStudent(registerUser);
        System.out.println("Student to save: " + student);
        Student savedStudent = studentRepository.save(student);
        System.out.println("Saved Student: " + savedStudent);
        String token = jwtService.generateToken(savedStudent);
        System.out.println("Generated Token: " + token);
        return token;
    }
    public String registrationTeacher(Register registerUser){
        return jwtService.generateToken(teacherRepository.save(userToTeacher(registerUser)));
    }

    public String auth(Login login) throws BadRequestException {
        manager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword()));
        Student student = studentRepository.findByEmail(login.getEmail());
        Teacher teacher = teacherRepository.findByEmail(login.getEmail());
        if(student!=null){
            return jwtService.generateToken(student);

        }else if(teacher!=null){
            return jwtService.generateToken(teacher);

        }else {
            throw new BadRequestException("User not found");
        }

    }
    public void deleteStudent(Long studentId)  {
        if(!studentRepository.existsById(studentId)){
            throw new UsernameNotFoundException("Error");
        }
        studentRepository.deleteById(studentId);

    }




    public Student userToStudent(Register user){
        return Student
                .builder()
                .name(user.getName())
                .role(RoleStudent.DEFAULT)
                .surname(user.getSurname())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail()).build();
    }
    public Teacher userToTeacher(Register user){
        return Teacher
                .builder()
                .name(user.getName())
                .role(RoleStudent.DEFAULT)
                .surname(user.getSurname())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail()).build();
    }
    public Student addStudent(Student student){
        return studentRepository.save(student);


    }






}
