package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Categories;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    ///////////////////////////
    // crud
    //////////////////////////

    Categories saveCategory(Categories entity);

    Optional<Categories> findCategoryById(long id);

    List<Categories> listCategories();

    long countCategories();

    void deleteCategoryById(long id);

    //////////////////////////
    // queries
    //////////////////////////

    List<Categories> findFirstByCategory(Pageable pageable);

    }
