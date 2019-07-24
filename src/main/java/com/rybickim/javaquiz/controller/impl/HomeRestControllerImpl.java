package com.rybickim.javaquiz.controller.impl;

import com.rybickim.javaquiz.controller.HomeRestController;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeRestControllerImpl implements HomeRestController {

    private static final Logger logger = LoggerFactory.getLogger(HomeRestControllerImpl.class);

    private CrudService<Questions> questionCrudService;

    @Autowired
    public HomeRestControllerImpl(CrudService<Questions> questionCrudService) {
        logger.debug("HomeRestControllerImpl(): " + questionCrudService);
        this.questionCrudService = questionCrudService;
    }

    @GetMapping("/rest/quiz")
    @Override
    public List<Questions> allQuizExercises() {
        logger.debug("allQuizExercises() from HomeRestControllerImpl");

        return questionCrudService.list();
    }
}
