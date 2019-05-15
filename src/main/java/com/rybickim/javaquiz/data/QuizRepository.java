package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.QuizExercise;

import java.util.List;

public interface QuizRepository {

    List<QuizExercise> readAllQuizExercises();
}
