package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class HomeControllerImpl implements HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

    private QuestionService questionService;
    private List<Questions> questionsToShow;

    public HomeControllerImpl(QuestionService questionService) {
        this.questionService = questionService;
        this.questionsToShow = questionService.listQuestions();
    }

    @PostMapping({"/","/home"})
    @Override
    public String removeElement(){
        logger.debug("removeElement()");

        if(!questionsToShow.isEmpty()){
            questionsToShow.remove(0);
            logger.debug("list element removed");
        }

        return "home";
    }

    @GetMapping({"/","/home"})
    @Override
    public String homePage(Model dataModel) {
        logger.debug("homePage()");

        int quizExerciseCount = questionsToShow.size();
        logger.debug("quizExerciseCount: {}", quizExerciseCount);


        String question = "";
        String answer = "";

        if (!questionsToShow.isEmpty()){
            question = questionsToShow.get(0).getQuestion();
            //TODO answers
//            answer = questionsToShow.get(0).getCategories().getCategoryName();
        }
        logger.debug("question: {}", question);
        logger.debug("answer: {}", answer);


        dataModel.addAttribute("question", question);
        dataModel.addAttribute("answer", answer);
        dataModel.addAttribute("count", quizExerciseCount);

        return "home";
    }
}
