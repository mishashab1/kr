package com.example.back.repository;

import com.example.back.model.AnswerOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOptions, Long> {
}
