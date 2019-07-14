package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Questions;

import java.util.List;

public interface QuestionService {

    long save(Questions question);

    Questions findById(long id);

    List<Questions> list();

    void update(Questions question);

    void deleteById(long id);
}
