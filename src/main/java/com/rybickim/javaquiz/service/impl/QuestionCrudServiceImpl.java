package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.QuestionsCrudRepository;
import com.rybickim.javaquiz.data.QuestionsRepository;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.QuestionCrudService;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("questionCrudService")
public class QuestionCrudServiceImpl implements QuestionCrudService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionCrudServiceImpl.class);

    private QuestionsCrudRepository questionsCrudRepository;

    @Autowired
    public QuestionCrudServiceImpl(QuestionsCrudRepository questionsCrudRepository) {
        logger.debug("StartServiceImpl(): " + questionsCrudRepository);
        this.questionsCrudRepository = questionsCrudRepository;
    }

    @Override
    @Transactional
    public Questions save(Questions question) {
        return questionsCrudRepository.save(question);
    }

    @Override
    @Transactional
    public Questions findById(long id) {
        return questionsCrudRepository.findById(id).orElse(new Questions());
    }

    @Override
    @Transactional
    public List<Questions> list() {
        List<Questions> questions = new ArrayList<>();
        questionsCrudRepository.findAll().forEach(questions::add);
        return questions;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        questionsCrudRepository.deleteById(id);
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
