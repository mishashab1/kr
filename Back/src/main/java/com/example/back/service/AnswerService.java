package com.example.back.service;
import com.example.back.dto.response.AnswerDTO;
import com.example.back.model.Answer;
import com.example.back.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;
    private final AnswerOptionService answerOptionService;
    private final QuizService quizService;

    public void saveAnswers(List<AnswerDTO> answers) {
        for (AnswerDTO answer : answers) {
            Answer newAnswer = new Answer();
            newAnswer.setQuestion(questionService.getQuestionById(answer.getQuestionId()));
            if (answer.getAnswerOptionId() != null) {
                newAnswer.setAnswerOption(answerOptionService.getAnswerOptionById(answer.getAnswerOptionId()).orElse(null));
            }
            newAnswer.setText(answer.getText());
            answerRepository.save(newAnswer);
        }
        Long quizId = questionService.getQuizIdByQuestionId(answers.get(0).getQuestionId());
        quizService.markQuizAsCompleted(quizId);
    }


}



