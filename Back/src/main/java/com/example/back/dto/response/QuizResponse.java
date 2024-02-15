package com.example.back.dto.response;

public record QuizResponse(
        Long id,
        String name,
        String rules,
        Boolean isCompleted,
        Boolean isVerified,
        Long thematic_quiz_id,
        String thematic_quiz_name,
        Double totalPoints,
        Double maxPoints,
        Double averageTotalPoints

){}