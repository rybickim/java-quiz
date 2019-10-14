package com.rybickim.javaquiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionDTO {

    private String question;
    private String category;
    private String correctAnswer;
}
