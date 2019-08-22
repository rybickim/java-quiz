package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.ChosenQuestionCrudRepository;
import com.rybickim.javaquiz.data.ExplanationCrudRepository;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Explanations;
import com.rybickim.javaquiz.service.ChosenQuestionService;
import com.rybickim.javaquiz.service.ExplanationService;
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
public class ExplanationServiceImpl implements ExplanationService {

    private static final Logger logger = LoggerFactory.getLogger(ExplanationServiceImpl.class);

    private ExplanationCrudRepository explanationCrudRepository;

    @Autowired
    public ExplanationServiceImpl(ExplanationCrudRepository explanationCrudRepository) {
        logger.debug("ExplanationServiceImpl(): explanationCrudRepository [{}]",
                explanationCrudRepository);
        this.explanationCrudRepository = explanationCrudRepository;
    }

    ////////////////////////////
    // crud
    /////////////////////////////


    @Override
    public Explanations saveExplanation(Explanations entity) {
        return explanationCrudRepository.save(entity);
    }

    @Override
    public List<Explanations> listExplanations() {
        List<Explanations> explanations = new ArrayList<>();
        explanationCrudRepository.findAll().forEach(explanations::add);
        return explanations;
    }

    @Override
    public long countExplanations() {
        return explanationCrudRepository.count();
    }

    @Override
    @Transactional
    public Optional<Explanations> findExplanationById(long id) {
        return explanationCrudRepository.findById(id);
    }

    @Override
    @Transactional
    public Iterable<Explanations> findAllExplanationsById(Iterable<Long> ids) {
        return explanationCrudRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public void deleteExplanationById(long id) {
        explanationCrudRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllExplanations() {
        explanationCrudRepository.deleteAll();
    }

    //////////////////////////////
    // queries
    /////////////////////////////

    @Override
    public List<Explanations> findFirstExplanations(Pageable pageable) {
        return explanationCrudRepository.findFirst(pageable);
    }

}
