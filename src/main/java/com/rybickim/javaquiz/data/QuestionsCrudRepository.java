package com.rybickim.javaquiz.data;

import com.rybickim.javaquiz.domain.Questions;
import org.springframework.data.repository.CrudRepository;

public interface QuestionsCrudRepository extends CrudRepository<Questions, Long> {
}
