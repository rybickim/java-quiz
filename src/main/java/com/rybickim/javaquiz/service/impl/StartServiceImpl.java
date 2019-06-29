package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.data.impl.DummyQuizRepository;
import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;
import com.rybickim.javaquiz.service.StartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Qualifier("service")
public class StartServiceImpl implements StartService {

    private static final Logger logger = LoggerFactory.getLogger(StartServiceImpl.class);

    private QuizEntityRepository quizEntityRepository;

    @Autowired
    public StartServiceImpl(@Qualifier("quizRepo") QuizEntityRepository quizEntityRepository) {
        logger.debug("StartServiceImpl(): " + quizEntityRepository);
        this.quizEntityRepository = quizEntityRepository;
    }

    @Override
    @Transactional
    public long save(QuizEntity quizEntity) {
        return quizEntityRepository.save(quizEntity);
    }

    @Override
    @Transactional
    public QuizEntity findById(long id) {
        return quizEntityRepository.get(id);
    }

    @Override
    @Transactional
    public List<QuizEntity> list() {
        return quizEntityRepository.list();
    }

    @Override
    @Transactional
    public void update(QuizEntity quizEntity) {
        quizEntityRepository.update(quizEntity);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        quizEntityRepository.delete(id);
    }

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
