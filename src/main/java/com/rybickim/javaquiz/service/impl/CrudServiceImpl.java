package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.CategoryCrudRepository;
import com.rybickim.javaquiz.data.ChosenQuestionCrudRepository;
import com.rybickim.javaquiz.data.QuestionCrudRepository;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CrudService;
import com.rybickim.javaquiz.utils.IncorrectDifficultyException;
import com.rybickim.javaquiz.utils.QuizDifficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CrudServiceImpl implements CrudService {

    private static final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);

    private QuestionCrudRepository questionCrudRepository;
    private CategoryCrudRepository categoryCrudRepository;
    private ChosenQuestionCrudRepository chosenQuestionCrudRepository;

    @Autowired
    public CrudServiceImpl(QuestionCrudRepository questionCrudRepository,
                           CategoryCrudRepository categoryCrudRepository,
                           ChosenQuestionCrudRepository chosenQuestionCrudRepository) {
        logger.debug("StartServiceImpl(): questionCrudRepository [{}], " +
                        "categoryCrudRepository [{}], " +
                        "chosenQuestionCrudRepository [{}]",
                questionCrudRepository,
                categoryCrudRepository,
                chosenQuestionCrudRepository);
        this.questionCrudRepository = questionCrudRepository;
        this.categoryCrudRepository = categoryCrudRepository;
        this.chosenQuestionCrudRepository = chosenQuestionCrudRepository;
    }

    @Override
    @Transactional
    public Questions saveQuestion(Questions entity) {
        return questionCrudRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<Questions> findQuestionById(long id) {
        return questionCrudRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Questions> listQuestions() {
        List<Questions> questions = new ArrayList<>();
        questionCrudRepository.findAll().forEach(questions::add);
        return questions;
    }

    @Override
    @Transactional
    public long countQuestions() {
        return questionCrudRepository.count();
    }

    @Override
    @Transactional
    public void deleteQuestionById(long id) {
        questionCrudRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Categories saveCategory(Categories entity) {
        return categoryCrudRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<Categories> findCategoryById(long id) {
        return categoryCrudRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Categories> listCategories() {
        List<Categories> categories = new ArrayList<>();
        categoryCrudRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    @Transactional
    public long countCategories() {
        return categoryCrudRepository.count();
    }

    @Override
    @Transactional
    public void deleteCategoryById(long id) {
        categoryCrudRepository.deleteById(id);
    }

    @Override
    @Transactional
    public long countQuestionsByCategory(Categories categories){
        return questionCrudRepository.countQuestionsByCategory(categories);
    }

    @Override
    @Transactional
    public List<Questions> findQuestionsWithNullCategory() {
        return questionCrudRepository.findQuestionsWithNullCategory();
    }

    @Override
    @Transactional
    public Page<Questions> findQuestionsWithCategory(Categories categories, Pageable pageable) {
        return questionCrudRepository.findQuestionsWithCategory(categories, pageable);
    }

    @Override
    @Transactional
    public Questions findFirstByQuestion(String question) {
        return questionCrudRepository.findFirstByQuestion(question);
    }

    @Override
    @Transactional
    public List<Categories> findFirstByCategory(Pageable pageable) {
        return categoryCrudRepository.findFirstByCategory(pageable);
    }

    @Override
    @Transactional
    public List<Questions> getShuffledAllQuestions(Categories category) {
        long totalRows = countQuestionsByCategory(category);

        Page<Questions> somePage = findQuestionsWithCategory(category, PageRequest.of(0,(int) totalRows));
        List<Questions> someList;
        if (somePage.getTotalElements() > 0) {
            someList = new ArrayList<>(somePage.getContent());
        } else {
            someList = new ArrayList<>();
        }

        Collections.shuffle(someList);

        return someList;
    }

    @Override
    @Transactional
    public List<Questions> getShuffledSelectedQuestions(QuizDifficulty qd, Categories category) throws IncorrectDifficultyException {
        long totalRows = countQuestionsByCategory(category);

        if (!getAvailableDifficulties((int) totalRows).contains(qd)){
            throw new IncorrectDifficultyException("Difficulty (" + qd.noOfQuestions +
                    ") exceeds the total number of questions (" + totalRows + ")");
        }

        Set<Questions> questions = new HashSet<>();

         while (questions.size() < qd.noOfQuestions) {
             int pageIndex = (int) (Math.random() * totalRows);

             questions.addAll(findQuestionsWithCategory(category, PageRequest.of(pageIndex, 1)).getContent());
         }

        List<Questions> resultList = new ArrayList<>(questions);

        Collections.shuffle(resultList);

        return resultList;
    }

    @Override
    public Set<QuizDifficulty> getAvailableDifficulties(int questionsInTotal){
        Set<QuizDifficulty> difficulties = new HashSet<>();

        for(QuizDifficulty qd : QuizDifficulty.values()){
            if (qd.noOfQuestions <= questionsInTotal){
                difficulties.add(qd);
            } else break;
        }

        return difficulties;
    }

    @Override
    public long countChosenQuestions() {
        return chosenQuestionCrudRepository.count();
    }

    @Override
    @Transactional
    public Optional<ChosenQuestions> findChosenQuestionById(long id) {
        return chosenQuestionCrudRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteChosenQuestionById(long id) {
        chosenQuestionCrudRepository.deleteById(id);
    }

    @Override
    public List<ChosenQuestions> findFirstChosenQuestions(Pageable pageable) {
        return chosenQuestionCrudRepository.findFirst(pageable);
    }

    @Override
    public void chooseQuestions(List<Questions> questions) {
        questions.forEach(x -> {

        });
    }

    @Override
    public void discardQuestions(List<Questions> questions) {

    }
}
