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

    @Query("SELECT ans FROM Answers ans WHERE TYPE(ans) = '1' AND ans.questions = :question")
    Optional<Answers> findCorrectValue(@Param("question") Questions question);

}
