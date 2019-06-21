package com.rybickim.javaquiz;

import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.service.StartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JavaQuizApplication.class },
                    loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    @Resource
    @Qualifier("quizRepo")
    QuizEntityRepository quizEntityRepository;

    @Test
    public void testIfDatabaseIsCreated(){

        logger.debug("--------------------------");
        logger.debug("testIfDatabaseIsCreated(): {}", quizEntityRepository);
        logger.debug("--------------------------");
        assertNotNull(quizEntityRepository);

    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }
}
