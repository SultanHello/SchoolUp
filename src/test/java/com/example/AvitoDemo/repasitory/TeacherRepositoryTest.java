package com.example.AvitoDemo.repasitory;

import com.example.AvitoDemo.Model.RoleStudent;
import com.example.AvitoDemo.Model.Student;
import com.example.AvitoDemo.Model.Teacher;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;
    //@Autowired
    //private PasswordEncoder passwordEncoder;
    @AfterEach
    void tearDown(){
        teacherRepository.deleteAll();
    }
    @Test
    void itShouldCheckIfStudentExistEmail() {
        String email = "12345";
        teacherRepository.save(Teacher
                .builder()
                .name("as")
                .role(RoleStudent.DEFAULT)
                .surname("asdsd")
                .password("12345")
                .email(email).build());



        //when
        boolean exist = teacherRepository.selectExist(email);
        //then
        assertThat(exist).isTrue();


    }
    @Test

    void itShouldCheckIfStudentDoestExistEmail() {
        String email = "12345";


        //when
        boolean exist = teacherRepository.selectExist(email);
        //then
        assertThat(exist).isFalse();


    }
}