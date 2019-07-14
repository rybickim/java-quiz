package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Questions;

import java.util.List;

public interface QuestionsRepository {

    Long save(Questions question);

    Questions get(Long id);

    List<Questions> list();

    void update(Questions question);

    void delete(Long id);
}
