package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Answers;
import com.rybickim.javaquiz.domain.ChosenQuestions;
import com.rybickim.javaquiz.domain.Questions;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AnswerCrudRepository
        extends CrudRepository<Answers, Long> {

    @Query("SELECT ans FROM Answers ans")
    List<Answers> findFirst(Pageable pageable);

    @Query(value = "SELECT a.answer_type_id FROM answers a " +
            "WHERE a.questions_question_id = :questionId",
    nativeQuery = true)
    Optional<Integer> findAnswerTypeByQuestion(@Param("questionId") Long questionId);

    @Query(value = "SELECT a.correctvalue FROM answers a " +
            "WHERE a.answer_type_id = 1 AND a.questions_question_id = :questionId",
    nativeQuery = true)
    Optional<String> findCorrectValue(@Param("questionId") Long questionId);

    @Query(value = "SELECT sentence FROM sentences_to_choose " +
            "WHERE mc_ordinal = (SELECT a.correctordinal FROM answers a " +
            "WHERE a.answer_type_id = 2 AND a.questions_question_id = :questionId)",
            nativeQuery = true)
    Optional<String> findCorrectOrdinal(@Param("questionId") Long questionId);

    @Query(value = "SELECT mw.missingterm FROM missing_words mw " +
            "INNER JOIN answers a ON mw.question_id = a.questions_question_id " +
            "WHERE mw.question_id = :questionId AND a.answer_type_id = 3 " +
            "ORDER BY mw.mg_ordinal ASC",
            nativeQuery = true)
    String[] findMissingTerms(@Param("questionId") Long questionId);

    //TODO change native queries into JPQL queries with JOIN if performance is the same or better and it's more readable

}
