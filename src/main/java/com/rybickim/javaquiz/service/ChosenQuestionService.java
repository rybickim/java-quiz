package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.ChosenQuestions;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChosenQuestionService {

    ////////////////////////////////////
    // crud
    ///////////////////////////////////

    ChosenQuestions saveChosenQuestion(ChosenQuestions entity);

    List<ChosenQuestions> listChosenQuestions();

    long countChosenQuestions();

    Optional<ChosenQuestions> findChosenQuestionById(long id);

    void deleteChosenQuestionById(long id);

    void deleteAllChosenQuestions();

    ///////////////////////////////////
    // queries
    /////////////////////////////////

    List<ChosenQuestions> findFirstChosenQuestions(Pageable pageable);
}
