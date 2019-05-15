package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Qualifier("service")
public class StartServiceImpl implements StartService {

    private static final Logger logger = LoggerFactory.getLogger(StartServiceImpl.class);

    @Override
    public List<QuizExercise> getQuizExercises() {
        logger.debug("getQuestions() from StartServiceImpl");

        return null;
    }


}
