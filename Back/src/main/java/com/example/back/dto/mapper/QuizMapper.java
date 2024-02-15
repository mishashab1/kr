package com.example.back.dto.mapper;

import com.example.back.dto.response.QuizResponse;
import com.example.back.model.Quiz;
import org.springframework.stereotype.Service;

@Service
public class QuizMapper {
    public QuizResponse mapWithPoints(Quiz quiz, double totalPoints, Double maxPoints, Double averageTotalPoints) {
        return new QuizResponse(
                quiz.getId(),
                quiz.getName(),
                quiz.getRules(),
                quiz.getCompleted(),
                quiz.getVerified(),
                quiz.getThematicQuiz().getId(),
                quiz.getThematicQuiz().getName(),
                totalPoints,
                maxPoints,
                averageTotalPoints
        );
    }
}
