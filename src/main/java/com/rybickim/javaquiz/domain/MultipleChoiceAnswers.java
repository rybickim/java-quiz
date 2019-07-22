package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "multiple_choice_answers")
public class MultipleChoiceAnswers extends Answers {

    Integer correctOrdinal;

    @OneToMany(mappedBy = "multipleChoiceAnswers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SentencesToChoose> sentencesToChoose = new ArrayList<>();

    public MultipleChoiceAnswers(Integer correctOrdinal) {
        this.correctOrdinal = correctOrdinal;
    }

    public void addSentence(SentencesToChoose sentence){
        sentencesToChoose.add(sentence);
        sentence.setMultipleChoiceAnswers(this);
    }

    public void removeSentence(SentencesToChoose sentence){
        sentencesToChoose.remove(sentence);
        sentence.setMultipleChoiceAnswers(null);
    }
}
