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

    private CrudService crudService;

    @Autowired
    public void setCrudService(CrudService crudService){
        this.crudService = crudService;
    }

    @Test
    public void testIfQuestionIsSaved(){


        // Given
        int questionsInDb = crudService.listQuestions().size();
        Questions question = createQuestion(questionsInDb + 1);

        // When
        Long savedQuizId = crudService.saveQuestion(question).getId();

        // Then
        assertEquals(questionsInDb + 1, crudService.listQuestions().size());
    }

    @Test
    public void testIfQuestionWithCategoryIsSaved(){

        // Given
        int questionsInDb = crudService.listQuestions().size();
        Questions question = createQuestionWithCategory(questionsInDb + 1);

        // When
        Long savedQuizId = crudService.saveQuestion(question).getId();

        // Then
        assertEquals(questionsInDb + 1, crudService.listQuestions().size());
    }

    @Test
    public void testIfQuestionWithCategoryAndMultipleChoiceAnswerIsSaved(){


        // Given
        int questionsInDb = crudService.listQuestions().size();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsInDb + 1);

        // When
        Long savedQuizId = crudService.saveQuestion(question).getId();

        // Then
        assertEquals(questionsInDb + 1, crudService.listQuestions().size());
    }

    @Test
    public void testIfQuestionIsNotFound(){
        // Given
        long nonExistingId = 1223232343434L;

        // When
        Optional<Questions> maybeQuestion = crudService.findQuestionById(nonExistingId);

        // Then
        assertEquals(Optional.empty(), maybeQuestion);
    }

    @Test
    public void testIfQuestionIsFound(){
        // Given
        int nextQuestionNumber = crudService.listQuestions().size() + 1;
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(nextQuestionNumber);
        Long savedQuizId = crudService.saveQuestion(question).getId();

        // When
        Questions maybeQuestion = crudService.findQuestionById(savedQuizId).orElse(new Questions());

        // Then
        assertNotNull(maybeQuestion);
        assertEquals(question, maybeQuestion);
    }

    @Test
    public void testIfQuestionIsDeleted(){

        // Given
        int questionsInDb = crudService.listQuestions().size();
        Questions question = createQuestion(questionsInDb + 1);

        Long savedQuizId = crudService.saveQuestion(question).getId();


        assertEquals(questionsInDb + 1, crudService.listQuestions().size());

        Optional<Questions> maybeQuestion = crudService.findQuestionById(savedQuizId);

        assertNotNull(maybeQuestion);
        assertNotEquals(Optional.empty(), maybeQuestion);

        // When
        crudService.deleteQuestionById(savedQuizId);
        maybeQuestion = crudService.findQuestionById(savedQuizId);

        // Then
        assertEquals(Optional.empty(), maybeQuestion);
    }

    // and now to make a test if the category isn't deleted with cascade...

    @Test
    public void testIfCategoryIsNotDeletedWhenQuestionIs(){
        // Given
        int questionsInDb = crudService.listQuestions().size();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsInDb + 1);
        Long savedQuizId = crudService.saveQuestion(question).getId();
        Long savedCategoryId = savedQuizId + 1L;

        Optional<Categories> maybeCategory = crudService.findCategoryById(savedCategoryId);

        assertNotEquals(Optional.empty(), maybeCategory);

        // When
        crudService.deleteQuestionById(savedQuizId);
        maybeCategory = crudService.findCategoryById(savedCategoryId);

        // Then

        assertNotEquals(Optional.empty(), maybeCategory);
    }

    @Test
    public void testIfCategoryIsSaved(){

        // Given
        int categoriesInDb = crudService.listCategories().size();
        Categories category = createCategory(categoriesInDb + 1);

        // When
        Long savedCategoryId = crudService.saveCategory(category).getId();

        // Then
        assertEquals(categoriesInDb + 1, crudService.listQuestions().size());
    }

    @Test
    public void testIfCategoryWithQuestionIsSaved(){

        // Given
        int categoriesInDb = crudService.listCategories().size();
        Categories category = createCategoryWithQuestion(categoriesInDb + 1);

        // When
        Long savedCategoryId = crudService.saveCategory(category).getId();

        // Then
        assertEquals(categoriesInDb + 1, crudService.listQuestions().size());
    }

    @Test
    public void testIfDatabaseHoldsNoDuplicates(){

    }

    /////////////////////////////////////////////
    //helper methods
    ///////////////////////////////////////////////////////////////

    private Questions createQuestion(int no){
        Questions question = new Questions("Question_" + no);

        logger.debug("Question - question: [{}]",
                question.getQuestion());

        return question;
    }

    private Questions createQuestionWithCategory(int no){
        Categories category = new Categories("Category_" + no);

        Questions question = new Questions("Question_" + no);

        category.addQuestion(question);

        logger.debug("Question - question: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getCategories().getCategory());

        return question;
    }

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

    private Categories createCategory(int no){
        Categories category = new Categories("Category_" + no);

        logger.debug("Category - category: [{}]",
                category.getCategory());

        return category;
    }

    private Categories createCategoryWithQuestion(int no){
        Categories category = new Categories("Category_" + no);

        Questions question = new Questions("Question_" + no);
        category.addQuestion(question);

        logger.debug("Category - category: [{}], question: [{}]",
                category.getCategory(),
                question.getQuestion());

        return category;
    }

}
