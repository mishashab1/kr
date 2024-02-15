package com.example.back.repository;

import com.example.back.dto.response.QuestionResponse;
import com.example.back.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT q.id AS id, q.text AS text, q.img AS img, q.max_points AS maxPoints, tq.name AS questionType, ao.id AS answerOptionsId, ao.text AS answerText, ao.is_right AS isRight " +
            "FROM question q " +
            "JOIN type_question tq ON tq.id = q.type_question_id " +
            "LEFT JOIN answer_options ao ON ao.question_id = q.id " +
            "WHERE q.quiz_id = :quizId",
            nativeQuery = true)
    List<Object[]> findAllQuizQuestions(Long quizId);

    @Query("SELECT SUM(q.maxPoints) FROM Question q WHERE q.quiz.id = :quizId")
    Integer findMaxPointsByQuizId(Long quizId);

    List<Question> findAllByQuizId(Long quizId);

    @Query(value = "SELECT q.type_question_id FROM webschema.question q WHERE q.id = :questionId", nativeQuery = true)
    Integer getTypeQuestionId(@Param("questionId") Long questionId);
}