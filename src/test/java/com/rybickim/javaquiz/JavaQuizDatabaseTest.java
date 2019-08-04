package com.rybickim.javaquiz;

import com.rybickim.javaquiz.config.DatabaseConfig;
import com.rybickim.javaquiz.domain.*;
import com.rybickim.javaquiz.service.CrudService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JavaQuizApplication.class, DatabaseConfig.class })
//@Transactional
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    private CrudService crudService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public void setCrudService(CrudService crudService){
        this.crudService = crudService;
    }


    @Test
    public void testIfQuestionIsSaved(){


        // Given
        long questionsCount = crudService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);

        // When
        crudService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, crudService.countQuestions());
    }

    @Test
    public void testIfQuestionIsUpdatedWithFirstCategory(){

        // Given
        long questionsCount = crudService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);
        Categories firstCategory = crudService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        question = crudService.saveQuestion(question);

        // When
        question.setCategories(firstCategory);
        crudService.saveCategory(firstCategory);
        crudService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, crudService.countQuestions());
    }

    @Test
    public void testIfQuestionWithCategoryIsSaved(){

        // Given
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();
        Questions question = createQuestionWithCategory(questionsCount + 1, categoriesCount + 1);

        // When
        crudService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, crudService.countQuestions());
    }

    @Test
    public void testIfQuestionWithCategoryAndMultipleChoiceAnswerIsSaved(){


        // Given
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsCount + 1, categoriesCount + 1);

        // When
        crudService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, crudService.countQuestions());
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
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(questionsCount + 1, categoriesCount + 1);
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
        long questionsCount = crudService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);

        Long savedQuizId = crudService.saveQuestion(question).getId();


        assertEquals(questionsCount + 1, crudService.countQuestions());

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
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsCount + 1, categoriesCount + 1);
        Long savedQuizId = crudService.saveQuestion(question).getId();
        long savedCategoryId = savedQuizId + 1L;

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
        long categoriesCount = crudService.countCategories();
        Categories category = createCategory(categoriesCount + 1);

        // When
        crudService.saveCategory(category);

        // Then
        assertEquals(categoriesCount + 1, crudService.countCategories());
    }

    @Test
    public void testIfCategoryWithQuestionIsSaved(){

        // Given
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();
        Categories category = createCategoryWithQuestion(categoriesCount + 1, questionsCount + 1);

        // When
        crudService.saveCategory(category);

        // Then
        assertEquals(categoriesCount + 1, crudService.countCategories());
    }

    @Test
    public void testIfCountEqualsListSize(){
        // Given
        int questionsInDb = crudService.listQuestions().size();

        // When
        int questionsCount = (int) crudService.countQuestions();

        // Then
        assertEquals(questionsCount, questionsInDb);
    }

    @Test
    public void testIfUnassignedQuestionsAreAllInFirstCategory(){
        // Given
        long questionsCount = crudService.countQuestions();
        long categoriesCount = crudService.countCategories();

        // When
        List<Questions> searchResult = crudService.findQuestionsWithEmptyCategory(null);

        assertNotEquals(Collections.EMPTY_LIST, searchResult);

        assertNotNull(searchResult.get(0).getId());
        assertEquals(Long.valueOf(143L),searchResult.get(0).getId());

        List<Categories> categoriesSearch = crudService.findFirstByCategory(PageRequest.of(0,1));

        assertNotEquals(Collections.EMPTY_LIST, categoriesSearch);
        assertEquals(1, categoriesSearch.size());

        for (Questions question : categoriesSearch.get(0).getQuestions()){
            logger.debug("Question from Category_1 BEFORE ADD: " + question);
        }

        for (Questions question : searchResult){
            categoriesSearch.get(0).addQuestion(question);
            crudService.saveQuestion(question);
//            entityManager.persist(question);
//            entityManager.merge(question);
//            entityManager.flush();
            logger.debug("Category from added question: " + question.getCategories());

        }

        for (Questions question : categoriesSearch.get(0).getQuestions()){
            logger.debug("Question from Category_1 AFTER ADD: " + question);
        }

        // Then
        searchResult = crudService.findQuestionsWithEmptyCategory(null);

        assertEquals(Collections.EMPTY_LIST, searchResult);

    }

    @Test
    public void testIfDatabaseHoldsNoDuplicateQuestions(){
        // Given

        // When

        // Then
    }

    /////////////////////////////////////////////
    //helper methods
    ///////////////////////////////////////////////////////////////

    private Questions createQuestion(long no){
        Questions question = new Questions("Question_" + no);

        logger.debug("Question - question: [{}]",
                question.getQuestion());

        return question;
    }

    private Questions createQuestionWithCategory(long questionNo, long categoryNo){
        Categories category = new Categories("Category_" + categoryNo);

        Questions question = new Questions("Question_" + questionNo);

        category.addQuestion(question);

        logger.debug("Question - question: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getCategories().getCategoryName());

        return question;
    }

    private Questions createQuestionWithCategoryAndTrueFalseAnswer(long questionNo, long categoryNo){
        Questions question = new Questions("Question_" + questionNo);

        Answers answer = new TrueFalseAnswers(Boolean.FALSE);
        question.addAnswer(answer);

        Categories category = new Categories("Category_" + categoryNo);
        category.addQuestion(question);

        logger.debug("Question - question: [{}], answer: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getAnswers(),
                question.getCategories().getCategoryName());

        return question;
    }

    private Questions createQuestionWithCategoryAndMultipleChoiceAnswer(long questionNo, long categoryNo){
        Questions question = new Questions("Question_" + questionNo);

        List<SentencesToChoose> sentences = Arrays.asList(
                new SentencesToChoose(1,"Sentence_" + questionNo),
                new SentencesToChoose(2,"Sentence_" + questionNo),
                new SentencesToChoose(3,"Sentence_" + questionNo));

        MultipleChoiceAnswers answer = new MultipleChoiceAnswers(2);

        answer.addSentences(sentences);

        question.addAnswer(answer);

        Categories category = new Categories("Category_" + categoryNo);
        category.addQuestion(question);

        logger.debug("Question - question: [{}], answer: [{}], category_name: [{}]",
                question.getQuestion(),
                question.getAnswers(),
                question.getCategories().getCategoryName());

        return question;
    }

    private Categories createCategory(long no){
        Categories category = new Categories("Category_" + no);

        logger.debug("Category - category_name: [{}]",
                category.getCategoryName());

        return category;
    }

    private Categories createCategoryWithQuestion(long categoryNo, long questionNo){
        Categories category = new Categories("Category_" + categoryNo);

        Questions question = new Questions("Question_" + questionNo);
        category.addQuestion(question);

        logger.debug("Category - category_name: [{}], question: [{}]",
                category.getCategoryName(),
                question.getQuestion());

        return category;
    }

}
