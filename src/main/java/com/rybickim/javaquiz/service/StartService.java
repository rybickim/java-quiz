package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.QuizExercise;

import java.util.List;
import java.util.Map;

public interface StartService {

//    Map<Integer, String> getQuestions();
//    Map<String, String> getAnswers();

    List<QuizExercise> getQuizExercises();

}
