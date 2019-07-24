package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Categories;
import org.springframework.data.repository.CrudRepository;

public interface GenericCrudRepository<T> extends CrudRepository<T, Long> {
}
