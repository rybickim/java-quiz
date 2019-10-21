package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.AnswerService;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ExplanationService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
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
    private AnswerService answerService;
    private ExplanationService explanationService;

    public HomeControllerImpl(QuestionService questionService,
                              AnswerService answerService,
                              ExplanationService explanationService) {
        this.questionService = questionService;
        this.questionsToShow = questionService.listQuestions();
        this.answerService = answerService;
        this.explanationService = explanationService;
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

    @GetMapping(value = {"/","/home"} //, produces = MediaType.IMAGE_PNG_VALUE
    )
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
            answer = String.join(", ",
                    answerService.giveCorrectAnswer(questionsToShow.get(0).getId()));
        }
        logger.debug("question: {}", question);
        logger.debug("answer: {}", answer);

        dataModel.addAttribute("question", question);
        dataModel.addAttribute("answer", answer);
        dataModel.addAttribute("count", quizExerciseCount);

        return "home";
    }
}
