package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Answers;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerCrudRepository
        extends CrudRepository<Answers, Long> {

    @Query("SELECT ans FROM Answers ans")
    List<Answers> findFirst(Pageable pageable);



}
