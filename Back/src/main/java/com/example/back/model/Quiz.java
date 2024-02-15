package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rules", nullable = false)
    private String rules;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @ManyToMany(mappedBy = "quizzes")
    Set<User> users;

    @ManyToOne
    @JoinColumn(name = "thematicQuiz_id", nullable = false)
    private ThematicQuiz thematicQuiz;
}