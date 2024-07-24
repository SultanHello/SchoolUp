package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Student,Long> {
    public Student findByEmail(String Email);
}
