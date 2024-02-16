package com.example.back.controller;

import com.example.back.dto.response.AnswerDTO;
import com.example.back.dto.response.QuestionResponse;
import com.example.back.dto.response.QuizResponse;
import com.example.back.model.Answer;
import com.example.back.service.AnswerService;
import com.example.back.service.QuestionService;
import com.example.back.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/server")
@RequiredArgsConstructor
public class MainController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    // SELECT * quiz WHERE completed = false AND user_id = ?
    @GetMapping("/getQuizzesCompletedFalse")
    public ResponseEntity<List<QuizResponse>> getQuizzesCompletedFalse(@RequestParam(name = "userId") Long userId) {
        List<QuizResponse> quizzes = quizService.findCompletedFalse(userId);
        return ResponseEntity.ok(quizzes);
    }

    // SELECT * quiz WHERE completed = true AND user_id = ?
    @GetMapping("/getQuizzesCompletedTrue")
    public ResponseEntity<List<QuizResponse>> getQuizzesCompletedTrue(@RequestParam(name = "userId") Long userId) {
        List<QuizResponse> quizzes = quizService.findCompletedTrue(userId);
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/getQuestions")
    public ResponseEntity<List<QuestionResponse>> getQuestions(@RequestParam(name = "quizId") Long quizId) {
        return ResponseEntity.ok(questionService.getAllQuizQuestions(quizId));
    }

    @PostMapping("/submitAnswers")
    public ResponseEntity<String> submitAnswers(@RequestBody List<AnswerDTO> answers) {
        answerService.saveAnswers(answers);
        return ResponseEntity.ok("Ответы сохранены");
    }

}

