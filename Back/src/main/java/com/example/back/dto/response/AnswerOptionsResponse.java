package com.example.back.dto.response;

import com.example.back.model.AnswerOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerOptionsResponse extends AnswerOptions {
    private Long id;
    private String text;
    private Boolean isRight;

    public AnswerOptionsResponse(Long id, String text, Boolean isRight) {
        this.id = id;
        this.text = text;
        this.isRight = isRight;
    }
}
