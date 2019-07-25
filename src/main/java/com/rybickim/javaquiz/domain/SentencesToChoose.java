package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "sentences_to_choose")
public class SentencesToChoose extends BaseClass<Long>{

    @Id
    @Column(name = "sentence_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "mc_ordinal", nullable = false)
    Integer ordinal;

    String sentence;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Answers multipleChoiceAnswers;

    public SentencesToChoose(Integer ordinal, String sentence) {
        this.ordinal = ordinal;
        this.sentence = sentence;
    }
}
