package com.example.back.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AnswerDTO {
    private Long questionId;
    private Long answerOptionId;
    private String text;
}

