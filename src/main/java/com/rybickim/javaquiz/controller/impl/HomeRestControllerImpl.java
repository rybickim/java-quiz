package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeRestController;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeRestControllerImpl implements HomeRestController {

    private static final Logger logger = LoggerFactory.getLogger(HomeRestControllerImpl.class);

    private StartService startService;

    @Autowired
    public HomeRestControllerImpl(@Qualifier("dummyService") StartService startService) {
        logger.debug("HomeRestControllerImpl(): " + startService);
        this.startService = startService;
    }

    @GetMapping("/rest/quiz")
    @Override
    public List<QuizExercise> allQuizExercises() {
        logger.debug("allQuizExercises() from HomeRestControllerImpl");

        return startService.getQuizExercises();
    }
}
