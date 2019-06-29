package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QuizEntityRepository {

    Long save(QuizEntity quizEntity);

    QuizEntity get(Long id);

    List<QuizEntity> list();

    void update(QuizEntity quizEntity);

    void delete(Long id);
}
