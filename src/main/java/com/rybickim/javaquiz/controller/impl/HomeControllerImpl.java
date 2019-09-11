package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ExplanationService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private ExplanationService explanationService;

    public HomeControllerImpl(QuestionService questionService, ExplanationService explanationService) {
        this.questionService = questionService;
        this.questionsToShow = questionService.listQuestions();
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

    @GetMapping(value = {"/","/home"}, produces = MediaType.IMAGE_PNG_VALUE)
    @Override
    public String homePage(Model dataModel) {
        logger.debug("homePage()");

        int quizExerciseCount = questionsToShow.size();
        logger.debug("quizExerciseCount: {}", quizExerciseCount);


        String question = "";
        String answer = "";
        String base64image = "";

        if (!questionsToShow.isEmpty()){
            question = questionsToShow.get(0).getQuestion();
            //TODO answers
//            answer = questionsToShow.get(0).getCategories().getCategoryName();
            base64image = Base64.getEncoder().encodeToString(explanationService.listExplanations().get(0).getExplanationDiagram());
        }
        logger.debug("question: {}", question);
        logger.debug("answer: {}", answer);
        //limited to substring to actually read the logs
        logger.debug("base64image: {}", base64image.substring(0,100));


        dataModel.addAttribute("question", question);
        dataModel.addAttribute("answer", answer);
        dataModel.addAttribute("count", quizExerciseCount);
        dataModel.addAttribute("image", base64image);

        return "home";
    }
}
