package com.example.back.service;

import com.example.back.dto.mapper.QuizMapper;
import com.example.back.dto.response.QuizResponse;
import com.example.back.model.Question;
import com.example.back.model.Quiz;
import com.example.back.model.User;
import com.example.back.repository.AnswerRepository;
import com.example.back.repository.QuestionRepository;
import com.example.back.repository.QuizRepository;
import com.example.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuizMapper quizMapper;

    public List<QuizResponse> findCompletedFalse(Long userId) {
        // Получение пользователя по ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        // Получение всех опросов, связанных с данным пользователем и где completed = false
        List<Quiz> quizzes = user.getQuizzes().stream()
                .filter(quiz -> !quiz.getCompleted())
                .collect(Collectors.toList());
        // Преобразование каждого Quiz в QuizResponse
        return quizzes.stream().map(quiz -> {
//            Integer totalPoints = answerRepository.findTotalPointsByQuizId(quiz.getId());
//            Integer maxPoints = questionRepository.findMaxPointsByQuizId(quiz.getId());
            Double totalPoints = (double) 0;
            Double maxPoints = (double) 0;
            Double averageTotalPoints = null;

            return quizMapper.mapWithPoints(quiz, totalPoints, maxPoints, averageTotalPoints);
        }).collect(Collectors.toList());
    }


    public List<QuizResponse> findCompletedTrue(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        List<Quiz> quizzes = user.getQuizzes().stream()
                .filter(Quiz::getCompleted)
                .collect(Collectors.toList());
        return quizzes.stream().map(quiz -> {
            double totalPoints;
            List<Double> pointsFromAnswers = answerRepository.findPointsByQuizId(quiz.getId());
            if (!pointsFromAnswers.contains(null)) {
                totalPoints = pointsFromAnswers.stream().mapToDouble(Double::doubleValue).sum();
            } else {
                List<Question> questions = questionRepository.findAllByQuizId(quiz.getId());
                totalPoints = 0.0;
                for (Question question : questions) {
                    long correctAnswersCount = answerRepository.countByQuestionIdAndIsRight(question.getId(), true);
                    long userCorrectAnswersCount = answerRepository.countByQuestionIdAndUserIdAndIsRight(question.getId(), userId, true);

                    if (correctAnswersCount > 0) {
                        Integer typeQuestionId = questionRepository.getTypeQuestionId(question.getId());
                        if (typeQuestionId != null && typeQuestionId == 2) {
                            double pointValue = question.getMaxPoints() / correctAnswersCount;
                            totalPoints += pointValue * userCorrectAnswersCount;
                        } else {
                            totalPoints += (double) userCorrectAnswersCount / correctAnswersCount;
                        }
                    }
                }
            }
            Double maxPoints = Double.valueOf(questionRepository.findMaxPointsByQuizId(quiz.getId()));
            Double averageTotalPoints = 0.0;
            return quizMapper.mapWithPoints(quiz, totalPoints, maxPoints, averageTotalPoints);
        }).collect(Collectors.toList());
    }


    public void markQuizAsCompleted(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id " + quizId));
        quiz.setCompleted(true);
        quizRepository.save(quiz);
    }

}


