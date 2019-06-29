package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;

import java.util.List;
import java.util.Map;

public interface StartService {

    long save(QuizEntity quizEntity);

    QuizEntity findById(long id);

    List<QuizEntity> list();

    void update(QuizEntity quizEntity);

    void deleteById(long id);

}
