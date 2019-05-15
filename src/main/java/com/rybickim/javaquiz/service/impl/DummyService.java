package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.impl.DummyQuizRepository;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("dummyService")
public class DummyService implements StartService {

    private static final Logger logger = LoggerFactory.getLogger(DummyService.class);

    private DummyQuizRepository dummyRepository;

    @Autowired
    public DummyService(@Qualifier("dummyRepo") DummyQuizRepository dummyRepository) {
        logger.debug("DummyService(): " + dummyRepository);
        this.dummyRepository = dummyRepository;
    }

    @Override
    public List<QuizExercise> getQuizExercises() {
        logger.debug("getQuestions() from DummyService");

        return dummyRepository.readAllQuizExercises();
    }
}
