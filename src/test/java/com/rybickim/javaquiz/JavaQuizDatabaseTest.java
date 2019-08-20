package com.rybickim.javaquiz;

import com.rybickim.javaquiz.config.DatabaseConfig;
import com.rybickim.javaquiz.domain.*;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ChosenQuestionService;
import com.rybickim.javaquiz.service.QuestionService;
import com.rybickim.javaquiz.utils.IncorrectDifficultyException;
import com.rybickim.javaquiz.utils.QuizDifficulty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(false)
@ContextConfiguration(classes = {JavaQuizApplication.class, DatabaseConfig.class })
public class JavaQuizDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaQuizDatabaseTest.class);

    private QuestionService questionService;
    private CategoryService categoryService;
    private ChosenQuestionService chosenQuestionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setChosenQuestionService(ChosenQuestionService chosenQuestionService) {
        this.chosenQuestionService = chosenQuestionService;
    }

    @Test
    public void testIfQuestionIsSaved(){


        // Given
        long questionsCount = questionService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);

        // When
        questionService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, questionService.countQuestions());
    }

    @Transactional
    @Test
    public void testIfQuestionIsSavedViaEntityManager(){


        // Given
        long questionsCount = questionService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);

        // When
        entityManager.persist(question);

        // Then
        assertEquals(questionsCount + 1, questionService.countQuestions());
    }

    @Transactional
    @Test
    public void testIfQuestionIsUpdatedWithFirstCategory(){

        // Given
        long questionsCount = questionService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        // persist transient bare question before update
        question = questionService.saveQuestion(question);

        // When
        question.setCategories(firstCategory);

        // Then
        assertEquals(questionsCount + 1, questionService.countQuestions());
    }

    @Test
    public void testIfQuestionWithCategoryIsSaved(){

        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Questions question = createQuestionWithCategory(questionsCount + 1, categoriesCount + 1);

        // When
        questionService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, questionService.countQuestions());
    }

    @Test
    public void testIfQuestionWithCategoryAndMultipleChoiceAnswerIsSaved(){


        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsCount + 1, categoriesCount + 1);

        // When
        questionService.saveQuestion(question);

        // Then
        assertEquals(questionsCount + 1, questionService.countQuestions());
    }

    @Test
    public void testIfQuestionIsNotFound(){
        // Given
        long nonExistingId = 1223232343434L;

        // When
        Optional<Questions> maybeQuestion = questionService.findQuestionById(nonExistingId);

        // Then
        assertEquals(Optional.empty(), maybeQuestion);
    }

    @Test
    public void testIfQuestionIsFound(){
        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Questions question = createQuestionWithCategoryAndTrueFalseAnswer(questionsCount + 1, categoriesCount + 1);
        isQuestionNew(question);
        Long savedQuizId = questionService.saveQuestion(question).getId();

        // When
        Questions maybeQuestion = questionService.findQuestionById(savedQuizId).orElse(new Questions());
        isQuestionNew(maybeQuestion);

        // Then
        assertNotNull(maybeQuestion);
        assertEquals(question, maybeQuestion);
    }

    @Test
    public void testIfQuestionIsDeleted(){

        // Given
        long questionsCount = questionService.countQuestions();
        Questions question = createQuestion(questionsCount + 1);

        Long savedQuizId = questionService.saveQuestion(question).getId();


        assertEquals(questionsCount + 1, questionService.countQuestions());

        Optional<Questions> maybeQuestion = questionService.findQuestionById(savedQuizId);

        assertNotNull(maybeQuestion);
        assertNotEquals(Optional.empty(), maybeQuestion);
        // When

        questionService.deleteQuestionById(savedQuizId);
        maybeQuestion = questionService.findQuestionById(savedQuizId);

        // Then
        assertEquals(Optional.empty(), maybeQuestion);
    }

    // and now to make a test if the category isn't deleted with cascade...

    @Test
    public void testIfCategoryIsNotDeletedWhenQuestionIs(){
        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Questions question = createQuestionWithCategoryAndMultipleChoiceAnswer(questionsCount + 1, categoriesCount + 1);
        Long savedQuizId = questionService.saveQuestion(question).getId();
        long savedCategoryId = savedQuizId + 1L;

        Optional<Categories> maybeCategory = categoryService.findCategoryById(savedCategoryId);

        assertNotEquals(Optional.empty(), maybeCategory);

        // When
        questionService.deleteQuestionById(savedQuizId);
        maybeCategory = categoryService.findCategoryById(savedCategoryId);

        // Then

        assertNotEquals(Optional.empty(), maybeCategory);
    }

    @Test
    public void testIfCategoryIsSaved(){

        // Given
        long categoriesCount = categoryService.countCategories();
        Categories category = createCategory(categoriesCount + 1);
        isCategoryNew(category);

        // When
        categoryService.saveCategory(category);

        // Then
        assertEquals(categoriesCount + 1, categoryService.countCategories());
    }

    @Transactional
    @Test
    public void testIfFoundCategoryNameCanBeUpdated(){

        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Categories category = createCategoryWithQuestion(categoriesCount + 1, questionsCount + 1);
        entityManager.persist(category);
        entityManager.flush();

        assertNotEquals(0, categoryService.countCategories());

        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);
        isCategoryNew(firstCategory);

        // When
        firstCategory.setCategoryName("Zerg_" + (categoriesCount + 1));
        logger.debug("Name changed");
        isCategoryNew(firstCategory);
        logger.debug("Name in the first category is: " + firstCategory.getCategoryName());

        // Then
        assertEquals(categoriesCount + 1, categoryService.countCategories());

    }

    @Transactional
    @Test
    public void testIfFoundCategoryQuestionsCanBeUpdated(){

        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Categories category = createCategoryWithQuestion(categoriesCount + 1, questionsCount + 1);
        entityManager.persist(category);
        entityManager.flush();

        assertNotEquals(0, categoryService.countCategories());

        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);
        Questions firstQuestion = questionService.findFirstByQuestion("Question_1");

        // When
        firstCategory.addQuestion(firstQuestion);

        // Then
        assertEquals(categoriesCount + 1, categoryService.countCategories());

    }

    @Test
    public void testIfCategoryWithQuestionIsSaved(){

        // Given
        long questionsCount = questionService.countQuestions();
        long categoriesCount = categoryService.countCategories();
        Categories category = createCategoryWithQuestion(categoriesCount + 1, questionsCount + 1);

        // When
        categoryService.saveCategory(category);

        // Then
        assertEquals(categoriesCount + 1, categoryService.countCategories());
    }

    @Test
    public void testIfCountEqualsListSize(){
        // Given
        int questionsInDb = questionService.listQuestions().size();

        // When
        int questionsCount = (int) questionService.countQuestions();

        // Then
        assertEquals(questionsCount, questionsInDb);
    }

    @Transactional
    @Test
    public void testIfUnassignedQuestionsAreSetToFirstCategory(){
        // Given
        long questionsCount = questionService.countQuestions();
        Questions unassignedQuestion = createQuestion(questionsCount + 1);

        entityManager.persist(unassignedQuestion);

        assertEquals(questionsCount + 1, questionService.countQuestions());

        // When
        List<Questions> searchResult = questionService.findQuestionsWithNullCategory();

        assertNotEquals(Collections.EMPTY_LIST, searchResult);

        assertNotNull(searchResult.get(0).getId());

        List<Categories> categoriesSearch = categoryService.findFirstByCategory(PageRequest.of(0,1));

        assertNotEquals(Collections.EMPTY_LIST, categoriesSearch);
        assertEquals(1, categoriesSearch.size());

        for (Questions question : searchResult){
            categoriesSearch.get(0).addQuestion(question);
        }

        // Then
        searchResult = questionService.findQuestionsWithNullCategory();

        assertEquals(Collections.EMPTY_LIST, searchResult);

    }

    @Test
    public void testIfCountQuestionsByCategoryWorks(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        assertNotNull(firstCategory);

        // When
        long result = questionService.countQuestionsByCategory(firstCategory);

        // Then
        assertEquals(result, questionService.countQuestionsByCategory(firstCategory));
    }

    @Test
    public void testIfFindQuestionsWithCategoryWorks(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        assertNotNull(firstCategory);

        // When
        List<Questions> resultList = questionService.findQuestionsWithCategory(firstCategory, PageRequest.of(0,3))
                .get()
                .collect(Collectors.toList());

        // Then
        assertEquals(questionService.countQuestionsByCategory(firstCategory), resultList.size());
    }

    // TODO thorough shuffle test
    @Transactional
    @Test
    public void testIfListIsShuffled(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        assertNotNull(firstCategory);

        int questionsCount = (int) questionService.countQuestionsByCategory(firstCategory);

        // When
        List<Questions> resultList = questionService.getShuffledAllQuestions(firstCategory);

        // Then
        assertEquals(questionsCount, resultList.size());
    }

    @Test
    public void testIfGetAvailableDifficultiesWorks(){
        // Given
        int size = 20;

        // When
        Set<QuizDifficulty> difficulties = questionService.getAvailableDifficulties(size);

        // Then
        assertNotNull(difficulties);
        assertEquals(2, difficulties.size());
        assertTrue(difficulties.contains(QuizDifficulty.EASY));
        assertTrue(difficulties.contains(QuizDifficulty.MEDIUM));
//        assertTrue(difficulties.contains(QuizDifficulty.HARD));
    }

    @Transactional
    @Test
    public void testIfSelectedQuestionsAreShuffled(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        assertNotNull(firstCategory);

        // When
        List<Questions> resultList = null;
        try {
            resultList = questionService.getShuffledSelectedQuestions( QuizDifficulty.EASY, firstCategory);
        } catch (IncorrectDifficultyException e) {
            e.printStackTrace();
        }

        // Then
        assertNotNull(resultList);
        assertEquals(QuizDifficulty.EASY.noOfQuestions, resultList.size());
    }

    @Transactional
    @Test(expected = IncorrectDifficultyException.class)
    public void testIfIncorrectDifficultyExceptionIsThrown() throws IncorrectDifficultyException{
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);

        assertNotNull(firstCategory);

        // When
        questionService.getShuffledSelectedQuestions( QuizDifficulty.HARD, firstCategory);

    }

    @Transactional
    @Test
    public void testIfCreatingSingleChosenQuestionWorks(){
        // Given
        long questionsInDb = questionService.countQuestions();
        Questions newQuestion = createQuestion(questionsInDb + 1);
        questionService.saveQuestion(newQuestion);
        long questionId = newQuestion.getId();

        // When
        ChosenQuestions chosenQuestion = createOrFindChosenQuestion(newQuestion.getId());
        chosenQuestionService.saveChosenQuestion(chosenQuestion);
        long chosenQuestionId = chosenQuestion.getId();

        // Then
        assertNotNull(chosenQuestion);
        assertEquals(chosenQuestionId, questionId);

    }

    @Transactional
    @Test
    public void testIfRemovingSingleChosenQuestionWorks(){
        // Given
        long questionsChosenInDb = chosenQuestionService.countChosenQuestions();
        long chosenQuestionId = chosenQuestionService.findFirstChosenQuestions(PageRequest.of(0,1)).get(0).getId();

        // When
        removeChosenQuestion(chosenQuestionId);

        // Then
        assertEquals(questionsChosenInDb - 1, chosenQuestionService.countChosenQuestions());

    }

    @Transactional
    @Test
    public void testIfCreating5ChosenQuestionsWorks(){
        // Given
        long questionsChosenInDb = chosenQuestionService.countChosenQuestions();
        List<Questions> first5Questions = questionService.findFirst5ByOrderByIdAsc();

        Iterable<Long> questionIds = first5Questions.stream()
                .map(Questions::getId)
                .collect(Collectors.toList());

        // When
        for (Questions question : first5Questions){
            createOrFindChosenQuestion(question.getId());
        }

        Iterable<Long> chosenQuestionIds = convertIterableToList(chosenQuestionService.findAllChosenQuestionsById(questionIds)).stream()
                .map(ChosenQuestions::getId)
                .collect(Collectors.toList());

        // Then
        assertNotNull(first5Questions);
        assertEquals(chosenQuestionIds, questionIds);

    }

    // TODO fix assertion error

    @Transactional
    @Test
    public void testIfCreatingAllChosenQuestionsFromCategoryWorks(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);
        List<Questions> questionsFromFirstCategory = questionService.findQuestionsWithCategory(firstCategory, PageRequest.of(0,10)).getContent();

        Iterable<Long> questionIds = questionsFromFirstCategory.stream()
                .map(Questions::getId)
                .collect(Collectors.toList());

        // When
        Iterable<ChosenQuestions> chosenQuestions = createOrFindMultipleChosenQuestions(questionIds);

        Iterable<Long> chosenQuestionIds = convertIterableToList(chosenQuestions).stream()
                .map(ChosenQuestions::getId)
                .collect(Collectors.toList());

        // Then
        assertNotNull(questionsFromFirstCategory);
        assertEquals(chosenQuestionIds, questionIds);

    }

    // TODO hardcoded values won't assert expectedly

    @Transactional
    @Test
    public void testIfRemovingAllChosenQuestionsFromCategoryWorks(){
        // Given
        Categories firstCategory = categoryService.findFirstByCategory(PageRequest.of(0,1)).get(0);
        List<Questions> questionsFromFirstCategory = questionService.findQuestionsWithCategory(firstCategory, PageRequest.of(0,10)).getContent();

        Iterable<Long> chosenQuestionIds = questionsFromFirstCategory.stream()
                .map(Questions::getId)
                .collect(Collectors.toList());

        // When
        removeMultipleChosenQuestions(chosenQuestionIds);

        // Then
        assertEquals(0, chosenQuestionService.countChosenQuestions());

    }

    @Transactional
    @Test
    public void testIfDeletingAllChosenQuestionsWorks(){
        // Given

        // When
        chosenQuestionService.deleteAllChosenQuestions();

        // Then
        assertEquals(0, chosenQuestionService.countChosenQuestions());

    }

    // TODO test explanation text and diagram

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

    private ChosenQuestions createOrFindChosenQuestion(long questionId){
        ChosenQuestions chosenQuestion = chosenQuestionService.findChosenQuestionById(questionId).orElse(new ChosenQuestions());

        questionService.findQuestionById(questionId).ifPresent(question -> question.addChosenQuestion(chosenQuestion));

        return chosenQuestion;
    }

    private void removeChosenQuestion(long chosenQuestionId){
        chosenQuestionService.findChosenQuestionById(chosenQuestionId).ifPresent(cq -> {
            questionService.findQuestionById(chosenQuestionId).ifPresent(question -> question.removeChosenQuestion(cq));
            chosenQuestionService.deleteChosenQuestionById(chosenQuestionId);
        });
    }

    private List<ChosenQuestions> createOrFindMultipleChosenQuestions(Iterable<Long> ids) {
        List<ChosenQuestions> chosenQuestions = new ArrayList<>();

        ids.forEach(id -> chosenQuestions.add(createOrFindChosenQuestion(id)));

        return chosenQuestions;
    }

    private void removeMultipleChosenQuestions(Iterable<Long> ids) {
        ids.forEach(this::removeChosenQuestion);
    }

    private boolean isQuestionNew(Questions question){
        Long id = question.getId();
        Class<Long> idType = Long.class;

        if (!idType.isPrimitive()) {
            logger.debug("isQuestionNew: first if clause: [{}]", id == null);
            return id == null;
        }

        if (id instanceof Number) {
            logger.debug("isQuestionNew: second if clause: [{}]", ((Number) id).longValue() == 0L);
            return ((Number) id).longValue() == 0L;
        }

        throw new IllegalArgumentException(String.format("Unsupported primitive id type %s!", idType));
    }

    private boolean isCategoryNew(Categories category){
        Long id = category.getId();
        Class<Long> idType = Long.class;

        if (!idType.isPrimitive()) {
            logger.debug("isCategoryNew: first if clause: [{}]", id == null);
            return id == null;
        }

        if (id instanceof Number) {
            logger.debug("isCategoryNew: second if clause: [{}]", ((Number) id).longValue() == 0L);
            return ((Number) id).longValue() == 0L;
        }

        throw new IllegalArgumentException(String.format("Unsupported primitive id type %s!", idType));
    }

    private <T> List<T> convertIterableToList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<>();

        iterable.forEach(resultList::add);

        return resultList;
    }
}
