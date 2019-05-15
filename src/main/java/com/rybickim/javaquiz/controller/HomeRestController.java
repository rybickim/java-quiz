package com.rybickim.javaquiz.controller;

import com.rybickim.javaquiz.domain.QuizExercise;

import java.util.List;
import java.util.Map;

public interface HomeRestController {

//    Map<Integer, String> quizQuestions();
//    Map<String, String> quizAnswers();

    List<QuizExercise> allQuizExercises();

}
