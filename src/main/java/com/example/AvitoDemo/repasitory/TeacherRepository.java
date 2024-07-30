package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    public Teacher findByEmail(String email);
}
