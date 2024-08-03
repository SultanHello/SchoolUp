package com.example.AvitoDemo.service;

import com.example.AvitoDemo.Exeptions.StudentNotFoundExceptions;
import com.example.AvitoDemo.Model.*;
import com.example.AvitoDemo.repasitory.StudentRepository;
import com.example.AvitoDemo.repasitory.TeacherRepository;
import jakarta.persistence.MapKeyJoinColumn;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Struct;

import static java.beans.Beans.isInstanceOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.awaitility.Awaitility.given;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest


class AppServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private AppService appService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager manager;
    @Mock
    private PasswordEncoder passwordEncoder;
    //private AutoCloseable autoCloseable;
//    @BeforeEach
//    void setUp(){
//       autoCloseable=MockitoAnnotations.openMocks(this);
//    }
//    @AfterEach
//    void tearDown() throws Exception{
//        autoCloseable.close();
//    }


    @Test
    void deleteStudent()  {
        Student student = new Student();
        student.setId(1L);
        student.setName("Sultan");
        Long studentId = 1L;

        when(studentRepository.existsById(1L)).thenReturn(false); // Имитация отсутствия студента


        assertThatThrownBy(()->appService.deleteStudent(studentId))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Error");
        verify(studentRepository).existsById(studentId); // Проверяем, что метод existsById был вызван
        verify(studentRepository, never()).deleteById(studentId);

    }
    @Test
    void deleteStudent1()  {
        Student student = new Student();
        student.setId(1L);
        student.setName("Sultan");
        Long studentId = 1L;

        when(studentRepository.existsById(1L)).thenReturn(true); // Имитация отсутствия студента
        appService.deleteStudent(1L);

//        assertThatThrownBy(()->appService.deleteStudent(studentId))
//                .isInstanceOf(UsernameNotFoundException.class)
//                .hasMessageContaining("Error");
        verify(studentRepository).existsById(studentId); // Проверка вызова метода existsById
        verify(studentRepository).deleteById(studentId);

    }
    @Test
    void getName() {
        // given
        String email = "12345";
        Student student = new Student();
        student.setEmail(email);
        student.setName("Sula");

        //when
        when(studentRepository.save(student)).thenReturn(student);
        Student savedStudent = studentRepository.save(student);

        // then
        assertThat(savedStudent.getName()).isEqualTo("Sula");
        verify(studentRepository).save(student);



    }



    @Test
    void registrationStudent() {
        // Given
        Register register = Register.builder()
                .name("Sultan")
                .surname("Assimbek")
                .email("asd123")
                .password("123")
                .build();

        Student student = Student.builder()
                .name(register.getName())
                .surname(register.getSurname())
                .email(register.getEmail())
                .password("encodedPassword")
                .build();

        String token = "token";

        // When
        when(passwordEncoder.encode(register.getPassword())).thenReturn("encodedPassword");
        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);
        when(studentRepository.findByEmail(register.getEmail())).thenReturn(null);
        when(jwtService.generateToken(ArgumentMatchers.any(Student.class))).thenReturn(token);

        // Act
        String jw = appService.registrationStudent(register);
//        ArgumentCaptor<Student> studentArgumentCaptor =ArgumentCaptor.forClass(Student.class);
//        verify(studentRepository).save(studentArgumentCaptor.capture());

        // Assert
        assertThat(jw).isEqualTo(token);

        // Verify
//        verify(passwordEncoder).encode(register.getPassword());
//        verify(studentRepository).save(ArgumentMatchers.any(Student.class));
//        verify(jwtService).generateToken(student);
    }
    @Test
    void addStudent(){
        Student student =new Student();
        appService.addStudent(student);
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());
        Student student1 = argumentCaptor.getValue();
        assertThat(student1).isEqualTo(student);
    }


    @BeforeEach
    void setUp() {
        System.out.println(1);
        // Инициализация объектов, подготовка мока
        //MockitoAnnotations.openMocks(this);
        // Другие необходимые настройки
    }

    @AfterEach
    void tearDown() {
        System.out.println(2);
        // Очистка ресурсов, сброс состояния
        // Например, закрытие соединений или файлов
    }

    @Test
    void registrationTeacher() {
        Teacher student = null;
        Register register  =Register.builder()
                .name("Sultan")
                .surname("Assimbek")
                .email("asd123")
                .password("123")
                .build();


        student = Teacher.builder()
                .name(register.getName())
                .surname(register.getSurname())
                .email(register.getEmail())
                .password(register.getPassword())
                .build();
        when(teacherRepository.save(student)).thenReturn(student);
        student =teacherRepository.save(student);
        assertThat(student.getName()).isEqualTo(register.getName());
        assertThat(student.getSurname()).isEqualTo(register.getSurname());
        assertThat(student.getEmail()).isEqualTo(register.getEmail());
        assertThat(student.getPassword()).isEqualTo(register.getPassword());

    }

    @Test
    void authStudent() throws BadCredentialsException, BadRequestException {
        Register register = Register.builder()
                .name("Sultan")
                .surname("Assimbek")
                .email("Sultan")
                .password("123")
                .build();

        // Создание объекта Student
        Student student = Student.builder()
                .name(register.getName())
                .surname(register.getSurname())
                .email(register.getEmail())
                .password(register.getPassword())
                .build();

        // Создание объекта Login
        Login login = new Login();
        login.setEmail("Sultan");
        login.setPassword("123");

        // Настройка моков
        when(studentRepository.findByEmail(login.getEmail())).thenReturn(student);
        when(jwtService.generateToken(student)).thenReturn("token");

        Authentication auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        when(manager.authenticate(ArgumentMatchers.any(Authentication.class))).thenReturn(auth);




        // Вызов метода
        String token = appService.auth(login);


        // Проверка результата
        assertThat(token).isEqualTo("token");




    }

    @Test
    void authStudentNull() throws BadRequestException {

        // Создание объекта Login
        Login login = new Login();
        login.setEmail("Sultan");
        login.setPassword("123");

        // Настройка моков
        when(teacherRepository.findByEmail(login.getEmail())).thenReturn(null);

        assertThatThrownBy(()->appService.auth(login)).isInstanceOf(BadRequestException.class).hasMessageContaining("User not found");

    }


    @Test
    void authTeacher() throws BadRequestException {
        Register register = Register.builder()
                .name("Sultan")
                .surname("Assimbek")
                .email("Sultan")
                .password("123")
                .build();

        // Создание объекта Student
        Teacher student = Teacher.builder()
                .name(register.getName())
                .surname(register.getSurname())
                .email(register.getEmail())
                .password(register.getPassword())
                .build();

        // Создание объекта Login
        Login login = new Login();
        login.setEmail("Sultan");
        login.setPassword("123");

        // Настройка моков
        when(teacherRepository.findByEmail(login.getEmail())).thenReturn(student);
        when(jwtService.generateToken(student)).thenReturn("token");

        Authentication auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        when(manager.authenticate(ArgumentMatchers.any(Authentication.class))).thenReturn(auth);




        // Вызов метода
        String token = appService.auth(login);

        // Проверка результата
        assertThat(token).isEqualTo("token");





    }


}