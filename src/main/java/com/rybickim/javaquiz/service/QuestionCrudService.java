package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Questions;

import java.util.List;

public interface QuestionCrudService {

    Questions save(Questions question);

    Questions findById(long id);

    List<Questions> list();

    void deleteById(long id);
}
