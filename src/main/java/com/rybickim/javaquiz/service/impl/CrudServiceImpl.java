package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.CategoryCrudRepository;
import com.rybickim.javaquiz.data.QuestionCrudRepository;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CrudServiceImpl implements CrudService {

    private static final Logger logger = LoggerFactory.getLogger(CrudServiceImpl.class);

    private QuestionCrudRepository questionCrudRepository;
    private CategoryCrudRepository categoryCrudRepository;

    @Autowired
    public CrudServiceImpl(QuestionCrudRepository questionCrudRepository,
                           CategoryCrudRepository categoryCrudRepository) {
        logger.debug("StartServiceImpl(): questionCrudRepository [{}], categoryCrudRepository [{}]",
                questionCrudRepository,
                categoryCrudRepository);
        this.questionCrudRepository = questionCrudRepository;
        this.categoryCrudRepository = categoryCrudRepository;
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
    public List<Questions> findQuestionsWithNullCategory() {
        return questionCrudRepository.findQuestionsWithNullCategory();
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

    // TODO shuffle implementation?
    //    @Override
//    public List<QuizEntity> getQuizExercises() {
//        logger.debug("getQuestions() from StartServiceImpl");
//
//        List<QuizEntity> result = new LinkedList<>();
//
//        quizEntityRepository.findAll().forEach( quizEntity -> result.add(quizEntity));
//
//        Collections.shuffle(result);
//
//        return result;
//    }


}
