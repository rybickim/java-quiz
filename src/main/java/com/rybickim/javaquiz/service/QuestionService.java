package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.utils.IncorrectDifficultyException;
import com.rybickim.javaquiz.utils.QuizDifficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionService {

    ////////////////////////
    // crud
    /////////////////////

    Questions saveQuestion(Questions entity);

    Optional<Questions> findQuestionById(long id);

    List<Questions> listQuestions();

    long countQuestions();

    void deleteQuestionById(long id);

    ////////////////////////
    //queries
    /////////////////////////

    long countQuestionsByCategory(Categories categories);

    List<Questions> findQuestionsWithNullCategory();

    Page<Questions> findQuestionsWithCategory(Categories categories, Pageable pageable);

    List<Questions> findFirstQuestions(Pageable pageable);

    Questions findFirstByQuestion(String question);

    List<Questions> findFirst5ByOrderByIdAsc();

    ////////////////////////////
    // shuffling
    //////////////////////////

    List<Questions> getShuffledAllQuestions(Categories category);

    List<Questions> getShuffledSelectedQuestions(QuizDifficulty qd, Categories category) throws IncorrectDifficultyException;

    Set<QuizDifficulty> getAvailableDifficulties(int questionsInTotal);

    ///////////////////////////////
    // choose/discard questions
    ////////////////////////////

    void chooseQuestions(List<Questions> questions);

    void discardQuestions(List<Questions> questions);

}
