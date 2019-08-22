package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Explanations;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExplanationService {

    ////////////////////////////////////
    // crud
    ///////////////////////////////////

    Explanations saveExplanation(Explanations entity);

    List<Explanations> listExplanations();

    long countExplanations();

    Optional<Explanations> findExplanationById(long id);

    Iterable<Explanations> findAllExplanationsById(Iterable<Long> id);

    void deleteExplanationById(long id);

    void deleteAllExplanations();

    ///////////////////////////////////
    // queries
    /////////////////////////////////

    List<Explanations> findFirstExplanations(Pageable pageable);
}
