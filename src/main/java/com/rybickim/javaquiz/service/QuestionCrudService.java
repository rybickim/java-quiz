package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Questions;

import java.util.List;
import java.util.Optional;

public interface QuestionCrudService {

    Questions save(Questions question);

    Optional<Questions> findById(long id);

    List<Questions> list();

    void deleteById(long id);
}
