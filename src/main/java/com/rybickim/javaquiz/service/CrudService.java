package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;

import java.util.List;
import java.util.Optional;

public interface CrudService {

    Questions saveQuestion(Questions entity);

    Optional<Questions> findQuestionById(long id);

    List<Questions> listQuestions();

    void deleteQuestionById(long id);

    Categories saveCategory(Categories entity);

    Optional<Categories> findCategoryById(long id);

    List<Categories> listCategories();

    void deleteCategoryById(long id);
}
