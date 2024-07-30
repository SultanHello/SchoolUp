package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.Student;
import com.example.AvitoDemo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    public Student findByEmail(String Email);
}
