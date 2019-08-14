package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.CategoryCrudRepository;
import com.rybickim.javaquiz.data.ChosenQuestionCrudRepository;
import com.rybickim.javaquiz.data.QuestionCrudRepository;
import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Questions;
import com.rybickim.javaquiz.service.CategoryService;
import com.rybickim.javaquiz.utils.IncorrectDifficultyException;
import com.rybickim.javaquiz.utils.QuizDifficulty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryCrudRepository categoryCrudRepository;

    @Autowired
    public CategoryServiceImpl(CategoryCrudRepository categoryCrudRepository) {
        logger.debug("CategoryServiceImpl(): categoryCrudRepository [{}]",
                categoryCrudRepository);
        this.categoryCrudRepository = categoryCrudRepository;
    }

    /////////////////////////
    // crud
    ////////////////////////

    @Override
    @Transactional
    public Categories saveCategory(Categories entity) {
        return categoryCrudRepository.save(entity);
    }

    @Override
    @Transactional
    public Optional<Categories> findCategoryById(long id) {
        return categoryCrudRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Categories> listCategories() {
        List<Categories> categories = new ArrayList<>();
        categoryCrudRepository.findAll().forEach(categories::add);
        return categories;
    }

    @Override
    @Transactional
    public long countCategories() {
        return categoryCrudRepository.count();
    }

    @Override
    @Transactional
    public void deleteCategoryById(long id) {
        categoryCrudRepository.deleteById(id);
    }

    /////////////////
    // queries
    /////////////////

    @Override
    @Transactional
    public List<Categories> findFirstByCategory(Pageable pageable) {
        return categoryCrudRepository.findFirstByCategory(pageable);
    }

}
