package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Questions;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    T save(T entity);

    Optional<T> findById(long id);

    List<T> list();

    void deleteById(long id);
}
