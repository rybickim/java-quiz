package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Categories;
import com.rybickim.javaquiz.domain.Questions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionCrudRepository
        extends CrudRepository<Questions, Long> {

    @Query("SELECT q FROM Questions q WHERE LOWER(q.categories) IS NULL")
    List<Questions> findQuestionsWithEmptyCategory(@Param("categories") Categories categories);
}
