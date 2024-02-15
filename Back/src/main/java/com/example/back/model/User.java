package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", length = 64, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 64)
    private String lastName;

    @Column(name = "surname", length = 64)
    private String surname;

    @Column(name = "email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 220, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_quizzez",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quizz_id"))
    private List<Quiz> quizzes;
}