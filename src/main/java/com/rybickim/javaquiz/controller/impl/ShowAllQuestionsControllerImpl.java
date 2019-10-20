package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.controller.ShowAllQuestionsController;
import com.rybickim.javaquiz.domain.*;
import com.rybickim.javaquiz.service.AnswerService;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ExplanationService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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
    private AnswerService answerService;

    public ShowAllQuestionsControllerImpl(QuestionService questionService, CategoryService categoryService, AnswerService answerService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.answerService = answerService;
    }


    @GetMapping(value = {"/showAllQuestions"})
    @Override
    public String showAllQuestions(Model dataModel) {
        logger.debug("showAllQuestions()");

        List<Questions> questions = questionService.listQuestions();

        String question;
        String category;
        String[] correctAnswer;

        List<QuestionDTO> questionDTOs = new ArrayList<>();

        for (Questions q : questions) {
            question = q.getQuestion();
            category = categoryService.findCategoryById(q.getCategories().getId())
                    .orElse(new Categories())
                    .getCategoryName();
            correctAnswer = answerService.giveCorrectAnswer(q.getId());

            questionDTOs.add(new QuestionDTOBuilder()
                    .setQuestion(question)
                    .setCategory(category)
                    .setCorrectAnswer(correctAnswer)
                    .buildQuestionDTO());

            logger.debug("question: {}", question);
            logger.debug("category: {}", category);
            logger.debug("correctAnswer: {}", correctAnswer.toString());
        }

        logger.debug("questions: {}", questionDTOs);

        dataModel.addAttribute("questions", questionDTOs);

        return "showAllQuestions";
    }
}
