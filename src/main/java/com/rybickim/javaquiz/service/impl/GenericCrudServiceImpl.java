package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.GenericCrudRepository;
import com.rybickim.javaquiz.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenericCrudServiceImpl<T> implements CrudService<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericCrudServiceImpl.class);

    private GenericCrudRepository<T> crudRepository;

    @Autowired
    public GenericCrudServiceImpl(GenericCrudRepository<T> crudRepository) {
        logger.debug("StartServiceImpl(): " + crudRepository);
        this.crudRepository = crudRepository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return crudRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<T> findById(long id) {
        return crudRepository.findById(id);
    }

    @Override
    @Transactional
    public List<T> list() {
        List<T> questions = new ArrayList<>();
        crudRepository.findAll().forEach(questions::add);
        return questions;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        crudRepository.deleteById(id);
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
