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

    @Column //shouldn't be unique because some questions might be repeated (e.g. case of multiple choice question: which one of the statements below is correct?)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Categories categories;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "questions",
            cascade = CascadeType.ALL, optional = false)
    private ChosenQuestions chosenQuestions;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "questions",
            cascade = CascadeType.ALL, optional = false)
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
