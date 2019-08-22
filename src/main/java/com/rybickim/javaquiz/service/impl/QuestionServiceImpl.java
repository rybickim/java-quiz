package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.QuestionCrudRepository;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.QuestionService;
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

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private QuestionCrudRepository questionCrudRepository;

    @Autowired
    public QuestionServiceImpl(QuestionCrudRepository questionCrudRepository) {
        logger.debug("QuestionServiceImpl():  questionCrudRepository: [{}]", questionCrudRepository);
        this.questionCrudRepository = questionCrudRepository;
    }

    /////////////////////
    // crud
    ////////////////////

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

    /////////////////////////////
    // queries
    ////////////////////////////

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
    public List<Questions> findFirstQuestions(Pageable pageable) {
        return questionCrudRepository.findFirst(pageable);
    }

    @Override
    @Transactional
    public Questions findFirstByQuestion(String question) {
        return questionCrudRepository.findFirstByQuestion(question);
    }

    @Override
    public List<Questions> findFirst5ByOrderByIdAsc() {
        return questionCrudRepository.findFirst5ByOrderByIdAsc();
    }

    ////////////////////
    // shuffling
    ////////////////////

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

    ///////////////////////////////
    // choose/discard questions
    ////////////////////////////

    @Override
    public void chooseQuestions(List<Questions> questions) {
        questions.forEach(x -> {

        });
    }

    @Override
    public void discardQuestions(List<Questions> questions) {

    }

}
