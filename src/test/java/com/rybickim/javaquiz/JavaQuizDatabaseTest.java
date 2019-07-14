package com.rybickim.javaquiz;

import com.rybickim.javaquiz.config.DatabaseConfig;
import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.domain.*;
import com.rybickim.javaquiz.service.QuestionCrudService;
import com.rybickim.javaquiz.service.QuestionService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaQuizApplication.class, DatabaseConfig.class })
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    private QuestionCrudService questionCrudService;

    @Autowired
    public void setQuestionService(@Qualifier("questionCrudService") QuestionCrudService questionCrudService){
        this.questionCrudService = questionCrudService;
    }

    @Test
    public void testIfQuestionIsSaved(){
        // Given
        int questionsInDb = questionCrudService.list().size();
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(questionsInDb + 1);

        // When
        Long savedQuizId = questionCrudService.save(question).getId();

        // Then
        assertEquals(questionsInDb + 1, questionCrudService.list().size());
    }

    @Test
    public void testIfQuizIsNotFound(){
        // Given
        long nonExistingId = 1223232343434L;

        // When
        Questions maybeQuestion = questionCrudService.findById(nonExistingId);

        // Then
        assertNull(maybeQuestion);
    }

    @Test
    public void testIfQuizIsFound(){
        // Given
        int nextQuestionNumber = questionCrudService.list().size() + 1;
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(nextQuestionNumber);
        Long savedQuizId = questionCrudService.save(question).getId();

        // When
        Questions maybeQuestion = questionCrudService.findById(savedQuizId);

        // Then
        assertNotNull(maybeQuestion);
    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }

    //helper methods
    private QuizEntity createQuiz(int quizNumber){
        QuizEntity quizEntity = new QuizEntity("question" + quizNumber,"answer" + quizNumber);
        return quizEntity;
    }

    private Questions createQuestionWithCategoryAndTrueFalseAnswer(int no){
        Questions question = new Questions("Question_" + no);

        Categories category = new Categories("Category_" + no);
        category.addQuestion(question);

        Answers answer = new TrueFalseAnswers(Boolean.TRUE);
        question.setAnswers(answer);

        ChosenQuestions chosenQuestions = new ChosenQuestions();
        question.setChosenQuestions(chosenQuestions);

        return question;
    }


}
