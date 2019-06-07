package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.QuizEntity;
import com.rybickim.javaquiz.domain.QuizExercise;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("quizRepo")
public interface QuizEntityRepository extends CrudRepository<QuizEntity, Long> {

}
