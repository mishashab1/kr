package com.example.back.repository;

import com.example.back.model.Quiz;
import com.example.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query(value = "SELECT u.id FROM webschema.user u INNER JOIN webschema.user_quizzez uq ON u.id = uq.user_id WHERE uq.quizz_id = :quizId", nativeQuery = true)
    List<Long> findUserIdsByQuizId(@Param("quizId") Long quizId);

    @Query(value = "SELECT AVG(uq.totalPoints) FROM UserQuiz uq WHERE uq.quiz.id = :quizId", nativeQuery = true)
    Double findAverageTotalPointsByQuizId(@Param("quizId") Long quizId);

}