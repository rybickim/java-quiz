package com.rybickim.javaquiz.controller;

import org.springframework.ui.Model;

public interface AddQuestionController {

        String addQuestion(Model dataModel, String question, String category);
        String addQuiz(Model dataModel);
}
