package com.example.back.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@Entity
@Table(name = "answerOptions")
public class AnswerOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "isRight")
    private Boolean isRight;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}