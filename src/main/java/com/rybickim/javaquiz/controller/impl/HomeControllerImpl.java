package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeController;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.json.JSONObject;
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

    private StartService startService;
    private Set<Integer> questionsDrawn;
    private List<QuizExercise> quizExercisesToShow;

    public HomeControllerImpl(@Qualifier("dummyService") StartService startService) {
        this.startService = startService;
        this.questionsDrawn = new HashSet<>();
        this.quizExercisesToShow = startService.getQuizExercises();
    }

    public int generateIndex(int listSize){
        return (int) Math.floor(Math.random() * listSize);
    }

    @PostMapping({"/","/home"})
    @Override
    public String postJson(Model dataModel, @RequestBody(required = false) String data){
        logger.debug("postJson()");
        JSONObject jsonObject;
        if (null != data){
            jsonObject = new JSONObject(data);
        } else {
            jsonObject = new JSONObject("{}");
        }

        int value = Integer.parseInt(jsonObject.get("index").toString());

        questionsDrawn.add(value);
        logger.debug("jsonObject: " + jsonObject);

        quizExercisesToShow.remove(value);
        logger.debug("list element removed");

        return "home";
    }

    @GetMapping({"/","/home"})
    @Override
    public String homePage(Model dataModel) {
        logger.debug("homePage()");

        int quizExerciseCount = quizExercisesToShow.size();
//        int index = generateIndex(quizExerciseCount);

        dataModel.addAttribute("quizzes", quizExercisesToShow);
        dataModel.addAttribute("count", quizExerciseCount);
        dataModel.addAttribute("index", 0);
        dataModel.addAttribute("questionsDrawn", questionsDrawn);
//        dataModel.addAttribute("randomIndex", generateIndex(quizExerciseCount.intValue()));

        return "home";
    }
}
