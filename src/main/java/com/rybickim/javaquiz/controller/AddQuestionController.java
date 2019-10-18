package com.rybickim.javaquiz.controller;

import org.springframework.ui.Model;

public interface AddQuestionController {

        String addQuestion(Model dataModel);
        String addQuiz(Model dataModel);
}
