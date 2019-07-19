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
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Questions questions;

}
