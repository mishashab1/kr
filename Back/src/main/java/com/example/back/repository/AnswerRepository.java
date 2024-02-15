package com.example.back.repository;

import com.example.back.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT COUNT(ao) FROM AnswerOptions ao WHERE ao.question.id = :questionId AND ao.isRight = :isRight")
    Integer countByQuestionIdAndIsRight(@Param("questionId") Long questionId, @Param("isRight") Boolean isRight);

    @Query(value = "SELECT COUNT(*) FROM webschema.answer AS a JOIN webschema.answer_options AS ao ON a.answer_option_id = ao.id JOIN webschema.question AS q ON a.question_id = q.id JOIN webschema.quiz AS quiz ON q.quiz_id = quiz.id JOIN webschema.user_quizzez AS uq ON quiz.id = uq.quizz_id JOIN webschema.user AS u ON uq.user_id = u.id WHERE q.id = ?1 AND u.id = ?2 AND ao.is_right = ?3", nativeQuery = true)
    Integer countByQuestionIdAndUserIdAndIsRight(Long questionId, Long userId, Boolean isRight);

    @Query("SELECT a.points FROM Answer a JOIN a.question q WHERE q.quiz.id = :quizId")
    List<Double> findPointsByQuizId(@Param("quizId") Long quizId);
}