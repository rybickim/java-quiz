package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoryCrudRepository
        extends CrudRepository<Categories, Long> {
}
