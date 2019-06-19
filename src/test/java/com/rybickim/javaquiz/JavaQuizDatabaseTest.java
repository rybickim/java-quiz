package com.rybickim.javaquiz;

import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.service.StartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    @Qualifier("service")
    StartService startService;

//    @Qualifier("quizRepo")
//    QuizEntityRepository repository;

    @Test
    public void testIfDatabaseIsCreated(){

        logger.debug("Quizzes found with findAll():");
        logger.debug("-------------------------------");
        startService.getQuizExercises().forEach(quiz -> logger.debug(quiz.toString()));
        logger.debug("");

    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }
}
