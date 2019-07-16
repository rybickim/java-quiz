package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "questions")
public class Questions {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Categories categories;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "choice_id")
    private ChosenQuestions chosenQuestions;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "answer_id")
    private Answers answers;

    public Questions(String question){
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Questions )) return false;
        return id != null && id.equals(((Questions) o).getId());
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
