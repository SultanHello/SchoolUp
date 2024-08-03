package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    public Teacher findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Teacher s WHERE s.email = ?1")
    public boolean selectExist(@Param("email")String email);

}
