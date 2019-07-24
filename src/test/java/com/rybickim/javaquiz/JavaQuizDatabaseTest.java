package com.rybickim.javaquiz;

import com.rybickim.javaquiz.config.DatabaseConfig;
import com.rybickim.javaquiz.domain.*;
import com.rybickim.javaquiz.service.CrudService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaQuizApplication.class, DatabaseConfig.class })
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    private CrudService<Questions> questionCrudService;
    private CrudService<Categories> categoryCrudService;

    @Autowired
    public void setQuestionService(CrudService<Questions> questionCrudService){
        this.questionCrudService = questionCrudService;
    }

    @Autowired
    public void setCategoryService(CrudService<Categories> categoryCrudService){
        this.categoryCrudService = categoryCrudService;
    }

    @Test
    public void testIfQuestionIsSaved(){


        // Given
        int questionsInDb = questionCrudService.list().size();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsInDb + 1);

        // When
        Long savedQuizId = questionCrudService.save(question).getId();

        // Then
        assertEquals(questionsInDb + 1, questionCrudService.list().size());
    }

    @Test
    public void testIfQuestionIsNotFound(){
        // Given
        long nonExistingId = 1223232343434L;

        // When
        Optional<Questions> maybeQuestion = questionCrudService.findById(nonExistingId);

        // Then
        assertEquals(Optional.empty(), maybeQuestion);
    }

    @Test
    public void testIfQuestionIsFound(){
        // Given
        int nextQuestionNumber = questionCrudService.list().size() + 1;
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(nextQuestionNumber);
        Long savedQuizId = questionCrudService.save(question).getId();

        // When
        Questions maybeQuestion = questionCrudService.findById(savedQuizId).orElse(new Questions());

        // Then
        assertNotNull(maybeQuestion);
        assertEquals(question, maybeQuestion);
    }

    @Test
    public void testIfFoundQuestionIsDeleted(){
        // Given
        long exampleId = 75L;
        Optional<Questions> maybeQuestion = questionCrudService.findById(exampleId);

        assertNotNull(maybeQuestion);
        assertNotEquals(Optional.empty(), maybeQuestion);

        // When
        questionCrudService.deleteById(exampleId);
        Optional<Questions> maybeQuestionAfterDeletion = questionCrudService.findById(exampleId);

        // Then
        assertEquals(Optional.empty(), maybeQuestionAfterDeletion);
    }

    // and now to make a test if the category isn't deleted with cascade...

    @Test
    public void testIfCategoryIsNotDeletedWhenQuestionIs(){
        // Given
        int questionsInDb = questionCrudService.list().size();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsInDb + 1);
        Long savedQuizId = questionCrudService.save(question).getId();
        Long savedCategoryId = savedQuizId + 1L;

        Optional<Categories> maybeCategory = categoryCrudService.findById(savedCategoryId);

        assertNotEquals(Optional.empty(), maybeCategory);

        // When
        questionCrudService.deleteById(savedQuizId);
        maybeCategory = categoryCrudService.findById(savedCategoryId);

        // Then

        assertNotEquals(Optional.empty(), maybeCategory);
    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }

    //helper methods

    private Questions createQuestionWithCategoryAndTrueFalseAnswer(int no){
        Questions question = new Questions("Question_" + no);

        Answers answer = new TrueFalseAnswers(Boolean.FALSE);
        question.addAnswer(answer);

        Categories category = new Categories("Category_" + no);
        category.addQuestion(question);

        logger.debug("Question - question: [{}], answer: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getAnswers(),
                question.getCategories().getCategory());

        return question;
    }

    private Questions createQuestionWithCategoryAndMultipleChoiceAnswer(int no){
        Questions question = new Questions("Question_" + no);

        List<SentencesToChoose> sentences = Arrays.asList(
                new SentencesToChoose(1,"Sentence_" + no),
                new SentencesToChoose(2,"Sentence_" + no),
                new SentencesToChoose(3,"Sentence_" + no));

        MultipleChoiceAnswers answer = new MultipleChoiceAnswers(2);

        answer.addSentences(sentences);

        question.addAnswer(answer);

        Categories category = new Categories("Category_" + no);
        category.addQuestion(question);

        logger.debug("Question - question: [{}], answer: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getAnswers(),
                question.getCategories().getCategory());

        return question;
    }


}
