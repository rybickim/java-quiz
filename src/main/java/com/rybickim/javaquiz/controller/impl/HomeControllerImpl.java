package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.data.QuestionsCrudRepository;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.QuestionCrudService;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Controller
public class HomeControllerImpl implements HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

    private QuestionCrudService startService;
    private List<Questions> quizExercisesToShow;

    public HomeControllerImpl(QuestionCrudService startService) {
        this.startService = startService;
        this.quizExercisesToShow = startService.list();
    }

    @PostMapping({"/","/home"})
    @Override
    public String removeElement(){
        logger.debug("removeElement()");

        if(!quizExercisesToShow.isEmpty()){
            quizExercisesToShow.remove(0);
            logger.debug("list element removed");
        }

        return "home";
    }

    @GetMapping({"/","/home"})
    @Override
    public String homePage(Model dataModel) {
        logger.debug("homePage()");

        int quizExerciseCount = quizExercisesToShow.size();
        logger.debug("quizExerciseCount: {}", quizExerciseCount);


        String question = "";
        String answer = "";

        if (!quizExercisesToShow.isEmpty()){
            question = quizExercisesToShow.get(0).getQuestion();
            //TODO answers
            answer = quizExercisesToShow.get(0).getCategories().getCategory();
        }
        logger.debug("question: {}", question);
        logger.debug("answer: {}", answer);


        dataModel.addAttribute("question", question);
        dataModel.addAttribute("answer", answer);
        dataModel.addAttribute("count", quizExerciseCount);

        return "home";
    }
}
