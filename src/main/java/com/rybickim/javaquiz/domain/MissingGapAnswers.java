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
@Table(name = "missing_gap_answers")
public class MissingGapAnswers extends Answers {

    @OneToMany(mappedBy = "missingGapAnswers", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MissingWords> missingWords = new ArrayList<>();

    public void addMissingWord(MissingWords missingWord){
        missingWords.add(missingWord);
        missingWord.setMissingGapAnswers(this);
    }

    public void removeMissingWord(MissingWords missingWord){
        missingWords.remove(missingWord);
        missingWord.setMissingGapAnswers(null);
    }
}
