package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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

    List<Questions> findQuestionsWithNullCategory();

    Questions findFirstByQuestion(String question);

    List<Categories> findFirstByCategory(Pageable pageable);

}
