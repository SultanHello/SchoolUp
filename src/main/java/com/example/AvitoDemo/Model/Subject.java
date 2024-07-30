package com.example.AvitoDemo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameSub;




    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subjectUser",
            joinColumns = @JoinColumn(name="subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")

    )
    private List<Student> courseOwner;

    @ElementCollection
    @CollectionTable(name = "grades",joinColumns = @JoinColumn(name = "subject_id"))
    @MapKeyJoinColumn(name = "sdudent_id")
    private Map<Student,Integer> grade;




}
