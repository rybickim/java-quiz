package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "chosen_questions")
public class ChosenQuestions {

    @Id
    @Column(name = "choice_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
