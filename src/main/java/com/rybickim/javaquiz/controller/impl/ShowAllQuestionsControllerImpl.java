package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.controller.ShowAllQuestionsController;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.QuestionDTO;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ExplanationService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ShowAllQuestionsControllerImpl implements ShowAllQuestionsController {

    private static final Logger logger = LoggerFactory.getLogger(ShowAllQuestionsControllerImpl.class);

    private QuestionService questionService;
    private CategoryService categoryService;
//    private List<Questions> questionsToShow;
//    private ExplanationService explanationService;
//
    public ShowAllQuestionsControllerImpl(QuestionService questionService, CategoryService categoryService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
//        this.questionsToShow = questionService.listQuestions();
//        this.explanationService = explanationService;
    }


    @GetMapping(value = {"/showAllQuestions"})
    @Override
    public String showAllQuestions(Model dataModel) {
        logger.debug("showAllQuestions()");

        List<Questions> questions = questionService.listQuestions();

        String question;
        String category;
        String correctAnswer = "Dummy Correct Answer";

        List<QuestionDTO> questionDTOs = new ArrayList<>();

        for (Questions q : questions) {
            question = q.getQuestion();
            category = categoryService.findCategoryById(q.getCategories().getId())
                    .orElse(new Categories())
                    .getCategoryName();
            questionDTOs.add(new QuestionDTO(question,category,correctAnswer));
            logger.debug("question: {}", question);
            logger.debug("category: {}", category);
            logger.debug("correctAnswer: {}", correctAnswer);
        }

        logger.debug("questions: {}", questionDTOs);

        dataModel.addAttribute("questions", questionDTOs);

        return "showAllQuestions";
    }
}
