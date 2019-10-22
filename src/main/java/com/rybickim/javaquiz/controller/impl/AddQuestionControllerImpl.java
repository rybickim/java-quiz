package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.AddQuestionController;
import com.rybickim.javaquiz.controller.ShowAllQuestionsController;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.QuestionDTO;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.AnswerService;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AddQuestionControllerImpl implements AddQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(AddQuestionControllerImpl.class);

    private QuestionService questionService;
    private CategoryService categoryService;
    private AnswerService answerService;

    public AddQuestionControllerImpl(QuestionService questionService,
                                     CategoryService categoryService,
                                     AnswerService answerService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.answerService = answerService;
    }

    @PostMapping(value = {"/addQuiz/submit"})
    @Override
    public String addQuestion(Model dataModel,
                              @RequestParam String question,
                              @RequestParam String category)
    {
        logger.debug("addQuestion()");

        Questions q = createQuestionWithCategory(question, category);
        questionService.saveQuestion(q);

        return "addQuiz";
    }

    @GetMapping(value = {"/addQuiz"})
    @Override
    public String addQuiz(Model dataModel) {
        logger.debug("addQuiz()");

        return "addQuiz";
    }

    // helper methods
    // TODO decorator needed? or builder?

    private Questions createQuestion(long no){
        Questions question = new Questions("Question_" + no);

        logger.debug("Question - question: [{}]",
                question.getQuestion());

        return question;
    }

    private Questions createQuestionWithCategory(String questionName, String categoryName){
        Categories category = new Categories(categoryName);

        Questions question = new Questions(questionName);

        category.addQuestion(question);

        logger.debug("Question - question: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getCategories().getCategoryName());

        return question;
    }
}
