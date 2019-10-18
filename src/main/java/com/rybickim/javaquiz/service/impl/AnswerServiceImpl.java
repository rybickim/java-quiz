package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.AnswerCrudRepository;
import com.rybickim.javaquiz.data.ChosenQuestionCrudRepository;
import com.rybickim.javaquiz.domain.Answers;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private AnswerCrudRepository answerCrudRepository;

    @Autowired
    public AnswerServiceImpl(AnswerCrudRepository answerCrudRepository) {
        logger.debug("AnswerServiceImpl(): answerCrudRepository [{}]",
                answerCrudRepository);
        this.answerCrudRepository = answerCrudRepository;
    }

    ////////////////////////////
    // crud
    /////////////////////////////


    @Override
    public Answers saveAnswer(Answers entity) {
        return answerCrudRepository.save(entity);
    }

    @Override
    public List<Answers> listAnswers() {
        List<Answers> answers = new ArrayList<>();
        answerCrudRepository.findAll().forEach(answers::add);
        return answers;
    }

    @Override
    public long countAnswers() {
        return answerCrudRepository.count();
    }

    @Override
    @Transactional
    public Optional<Answers> findAnswerById(long id) {
        return answerCrudRepository.findById(id);
    }

    @Override
    public Iterable<Answers> findAllAnswersById(Iterable<Long> ids) {
        return answerCrudRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public void deleteAnswerById(long id) {
        answerCrudRepository.deleteById(id);
    }

    @Override
    public void deleteAllAnswers() {
        answerCrudRepository.deleteAll();
    }

    //////////////////////////////
    // queries
    /////////////////////////////


    @Override
    public List<Answers> findFirstAnswers(Pageable pageable) {
        return answerCrudRepository.findFirst(pageable);
    }

    @Override
    public String[] giveCorrectAnswer(long questionId) {
        switch (this.findAnswerTypeByQuestion(questionId)){
            case 1:
                return new String[]{ this.findCorrectValue(questionId) };
            case 2:
                return new String[]{ this.findCorrectOrdinal(questionId) };
            case 3:
                return this.findMissingTerms(questionId);
            case 0:
            default:
                break;
        }
        return new String[]{"N/A"};
    }

    private Integer findAnswerTypeByQuestion(long questionId){
        return answerCrudRepository.findAnswerTypeByQuestion(questionId).orElse(0);
    }

    private String findCorrectValue(Long questionId) {
        return answerCrudRepository.findCorrectValue(questionId).orElse("N/A");
    }

    private String findCorrectOrdinal(Long questionId) {
        return answerCrudRepository.findCorrectOrdinal(questionId).orElse("N/A");
    }

    private String[] findMissingTerms(Long questionId) {
        String[] strings = answerCrudRepository.findMissingTerms(questionId);
        return strings;
    }

}
