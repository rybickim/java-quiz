package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;

import java.util.List;

public interface QuizRepository {

    List<QuizEntity> readAllQuizExercises();
}
