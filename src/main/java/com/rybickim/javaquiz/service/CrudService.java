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

public interface CrudService {

    Questions saveQuestion(Questions entity);

    Optional<Questions> findQuestionById(long id);

    List<Questions> listQuestions();

    long countQuestions();

    void deleteQuestionById(long id);

    Categories saveCategory(Categories entity);

    Optional<Categories> findCategoryById(long id);

    List<Categories> listCategories();

    long countCategories();

    void deleteCategoryById(long id);

    long countQuestionsByCategory(Categories categories);

    List<Questions> findQuestionsWithNullCategory();

    Page<Questions> findQuestionsWithCategory(Categories categories, Pageable pageable);

    Questions findFirstByQuestion(String question);

    List<Categories> findFirstByCategory(Pageable pageable);

    List<Questions> getShuffledAllQuestions(Categories category);

    List<Questions> getShuffledSelectedQuestions(QuizDifficulty qd, Categories category) throws IncorrectDifficultyException;

    Set<QuizDifficulty> getAvailableDifficulties(int questionsInTotal);

    }
