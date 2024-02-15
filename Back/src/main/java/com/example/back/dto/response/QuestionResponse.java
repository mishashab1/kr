package com.example.back.dto.response;

import com.example.back.model.AnswerOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private String text;
    private String img;
    private Double maxPoints;
    private String questionType;
    private List<AnswerOptions> answerOptions;

    public QuestionResponse(Long id, String text, String img, int maxPoints, String questionType, List<AnswerOptions> answerOptions) {
        this.id = id;
        this.text = text;
        this.img = img;
        this.maxPoints = (double) maxPoints;
        this.questionType = questionType;
        this.answerOptions = answerOptions;
    }
}
