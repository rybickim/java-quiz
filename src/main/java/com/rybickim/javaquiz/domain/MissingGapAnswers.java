package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "missing_gap_answers")
public class MissingGapAnswers extends Answers {

    @ElementCollection
    private List<String> missingWords;
}