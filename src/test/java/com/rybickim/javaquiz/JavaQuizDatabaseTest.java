package com.rybickim.javaquiz;

import com.rybickim.javaquiz.config.DatabaseConfig;
import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.domain.QuizEntity;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaQuizApplication.class, DatabaseConfig.class })
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    private StartService startService;

    @Autowired
    public void setStartService(@Qualifier("service") StartService startService){
        this.startService = startService;
    }

    @Test
    public void testIfQuizIsSaved(){
        // Given
        int quizzesInDb = startService.list().size();
        QuizEntity quizEntity = createQuiz(quizzesInDb + 1);

        // When
        Long savedQuizId = startService.save(quizEntity);

        // Then
        assertEquals(quizzesInDb + 1, startService.list().size());
    }

    @Test
    public void testIfQuizIsNotFound(){
        // Given
        long nonExistingId = 1223232343434L;

        // When
        QuizEntity returnedQuiz = startService.findById(nonExistingId);

        // Then
        assertNull(returnedQuiz);
    }

    @Test
    public void testIfQuizIsFound(){
        // Given
        int nextQuizNumber = startService.list().size() + 1;
        QuizEntity quizEntity = createQuiz(nextQuizNumber);
        Long savedQuizId = startService.save(quizEntity);

        // When
        QuizEntity quizFromDb = startService.findById(savedQuizId);

        // Then
        assertNotNull(quizFromDb);
    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }

    //helper method
    private QuizEntity createQuiz(int quizNumber){
        QuizEntity quizEntity = new QuizEntity("question" + quizNumber,"answer" + quizNumber);
        return quizEntity;
    }
}
