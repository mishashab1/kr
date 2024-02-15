package com.example.back.service;

import com.example.back.model.AnswerOptions;
import com.example.back.repository.AnswerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerOptionService {

    private final AnswerOptionRepository answerOptionRepository;

    public Optional<AnswerOptions> getAnswerOptionById(Long id) {
        return answerOptionRepository.findById(id);
    }
}
