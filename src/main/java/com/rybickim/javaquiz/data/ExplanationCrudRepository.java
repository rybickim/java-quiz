package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Explanations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExplanationCrudRepository
        extends CrudRepository<Explanations, Long> {

    @Query("SELECT exp FROM Explanations exp")
    List<Explanations> findFirst(Pageable pageable);

}
