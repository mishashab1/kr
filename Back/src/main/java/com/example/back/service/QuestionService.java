package com.example.back.service;

import com.example.back.dto.response.AnswerOptionsResponse;
import com.example.back.dto.response.QuestionResponse;
import com.example.back.model.Question;
import com.example.back.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionResponse> getAllQuizQuestions(Long quizId) {
        List<Object[]> results = questionRepository.findAllQuizQuestions(quizId);
        Map<Long, QuestionResponse> questionMap = new HashMap<>();
        for (Object[] row : results) {
            Long questionId = (Long) row[0];
            if (!questionMap.containsKey(questionId)) {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setId(questionId);
                questionResponse.setText((String) row[1]);
                questionResponse.setImg((String) row[2]);
                questionResponse.setMaxPoints((Double) row[3]);
                questionResponse.setQuestionType((String) row[4]);
                questionResponse.setAnswerOptions(new ArrayList<>());
                questionMap.put(questionId, questionResponse);
            }
            if (row[5] != null) { // Check if answerOptionsId is not null
                AnswerOptionsResponse answerOptionsResponse = new AnswerOptionsResponse();
                answerOptionsResponse.setId((Long) row[5]);
                answerOptionsResponse.setText((String) row[6]);
                answerOptionsResponse.setIsRight((Boolean) row[7]);
                questionMap.get(questionId).getAnswerOptions().add(answerOptionsResponse);
            }
        }
        return new ArrayList<>(questionMap.values());
    }

    public Question getQuestionById(Long questionId) {
        return questionRepository.findById(questionId).orElse(null);
    }

    public Long getQuizIdByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + questionId));
        return question.getQuiz().getId();
    }
}
