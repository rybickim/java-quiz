package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.controller.ShowAllQuestionsController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.ExplanationService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ShowAllQuestionsControllerImpl implements ShowAllQuestionsController {

    private static final Logger logger = LoggerFactory.getLogger(ShowAllQuestionsControllerImpl.class);

//    private QuestionService questionService;
//    private List<Questions> questionsToShow;
//    private ExplanationService explanationService;
//
//    public ShowAllQuestionsControllerImpl(QuestionService questionService, ExplanationService explanationService) {
//        this.questionService = questionService;
//        this.questionsToShow = questionService.listQuestions();
//        this.explanationService = explanationService;
//    }


    @GetMapping(value = {"/showAllQuestions"})
    @Override
    public String showAllQuestions(Model dataModel) {
        logger.debug("showAllQuestions()");


        String question = "Dummy Question";
        String category = "Dummy Category";
        String correctAnswer = "Dummy Correct Answer";

        List<String> questions = Arrays.asList(question,category,correctAnswer);

        logger.debug("question: {}", question);
        logger.debug("question: {}", question);
        logger.debug("category: {}", category);
        logger.debug("correctAnswer: {}", correctAnswer);

        dataModel.addAttribute("questions", questions);
        dataModel.addAttribute("question", question);
        dataModel.addAttribute("category", category);
        dataModel.addAttribute("correctAnswer", correctAnswer);

        return "showAllQuestions";
    }
}
