package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.QuestionsRepository;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Qualifier("questionService")
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private QuestionsRepository questionsRepository;

    @Autowired
    public QuestionServiceImpl(@Qualifier("questionsRepo") QuestionsRepository questionsRepository) {
        logger.debug("StartServiceImpl(): " + questionsRepository);
        this.questionsRepository = questionsRepository;
    }

    @Override
    @Transactional
    public long save(Questions question) {
        return questionsRepository.save(question);
    }

    @Override
    @Transactional
    public Questions findById(long id) {
        return questionsRepository.get(id);
    }

    @Override
    @Transactional
    public List<Questions> list() {
        return questionsRepository.list();
    }

    @Override
    @Transactional
    public void update(Questions question) {
        questionsRepository.update(question);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        questionsRepository.delete(id);
    }

}
