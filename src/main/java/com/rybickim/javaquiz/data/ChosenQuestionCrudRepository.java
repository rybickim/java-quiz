package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.ChosenQuestions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChosenQuestionCrudRepository
        extends CrudRepository<ChosenQuestions, Long> {

    @Query("SELECT cq FROM ChosenQuestions cq")
    List<ChosenQuestions> findFirst(Pageable pageable);

}
