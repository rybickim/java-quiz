package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.Answers;
import com.rybickim.javaquiz.domain.Questions;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AnswerService {

    ////////////////////////////////////
    // crud
    ///////////////////////////////////

    Answers saveAnswer(Answers entity);

    List<Answers> listAnswers();

    long countAnswers();

    Optional<Answers> findAnswerById(long id);

    Iterable<Answers> findAllAnswersById(Iterable<Long> id);

    void deleteAnswerById(long id);

    void deleteAllAnswers();

    ///////////////////////////////////
    // queries
    /////////////////////////////////

    List<Answers> findFirstAnswers(Pageable pageable);

    String[] giveCorrectAnswer(long questionId);
}
