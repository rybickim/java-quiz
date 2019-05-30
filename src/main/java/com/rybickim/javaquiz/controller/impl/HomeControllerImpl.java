package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
public class HomeControllerImpl implements HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeControllerImpl.class);

    private StartService startService;

    public HomeControllerImpl(@Qualifier("dummyService") StartService startService) {
        this.startService = startService;
    }

    public int generateIndex(int listSize){
        return (int) Math.floor(Math.random() * listSize);
    }

    @GetMapping({"/","/home"})
    @Override
    public String homePage(Model dataModel) {
        logger.debug("homePage()");

        List<QuizExercise> quizExercisesToShow = startService.getQuizExercises();
        int quizExerciseCount = quizExercisesToShow.size();
        int index = generateIndex(quizExerciseCount);
        Set<Integer> questionsDrawn = new HashSet<>();
        questionsDrawn.add(8);

        dataModel.addAttribute("quizzes", quizExercisesToShow);
        dataModel.addAttribute("count", quizExerciseCount);
        dataModel.addAttribute("index", index);
        dataModel.addAttribute("questionsDrawn", questionsDrawn);
//        dataModel.addAttribute("randomIndex", generateIndex(quizExerciseCount.intValue()));

        return "home";
    }
}
