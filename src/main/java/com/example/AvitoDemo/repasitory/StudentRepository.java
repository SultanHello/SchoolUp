package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.Student;
import com.example.AvitoDemo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student,Long> {

    public Student findByEmail(String Email);
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Teacher s WHERE s.email = ?1")
    public boolean selectExist(@Param("email")String email);
}

