package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "multiple_choice_answers")
public class MultipleChoiceAnswers extends Answers {

    private List<String> sentencesToChoose;
}
