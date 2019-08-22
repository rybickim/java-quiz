package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.CategoryCrudRepository;
import com.rybickim.javaquiz.data.ChosenQuestionCrudRepository;
import com.rybickim.javaquiz.data.QuestionCrudRepository;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.service.ChosenQuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChosenQuestionServiceImpl implements ChosenQuestionService {

    private static final Logger logger = LoggerFactory.getLogger(ChosenQuestionServiceImpl.class);

    private ChosenQuestionCrudRepository chosenQuestionCrudRepository;

    @Autowired
    public ChosenQuestionServiceImpl(ChosenQuestionCrudRepository chosenQuestionCrudRepository) {
        logger.debug("ChosenQuestionServiceImpl(): chosenQuestionCrudRepository [{}]",
                chosenQuestionCrudRepository);
        this.chosenQuestionCrudRepository = chosenQuestionCrudRepository;
    }

    ////////////////////////////
    // crud
    /////////////////////////////


    @Override
    public ChosenQuestions saveChosenQuestion(ChosenQuestions entity) {
        return chosenQuestionCrudRepository.save(entity);
    }

    @Override
    public List<ChosenQuestions> listChosenQuestions() {
        List<ChosenQuestions> chosenQuestions = new ArrayList<>();
        chosenQuestionCrudRepository.findAll().forEach(chosenQuestions::add);
        return chosenQuestions;
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
    public Iterable<ChosenQuestions> findAllChosenQuestionsById(Iterable<Long> ids) {
        return chosenQuestionCrudRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public void deleteChosenQuestionById(long id) {
        chosenQuestionCrudRepository.deleteById(id);
    }

    @Override
    public void deleteAllChosenQuestions() {
        chosenQuestionCrudRepository.deleteAll();
    }

    //////////////////////////////
    // queries
    /////////////////////////////

    @Override
    public List<ChosenQuestions> findFirstChosenQuestions(Pageable pageable) {
        return chosenQuestionCrudRepository.findFirst(pageable);
    }
}
