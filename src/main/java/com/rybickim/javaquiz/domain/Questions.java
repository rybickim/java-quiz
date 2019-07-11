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

    @Id // no need for naming Column and GeneratedValue
    private Long id;

    @Column(unique = true)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @OneToOne(fetch = FetchType.LAZY) //read vlad mihalcea for an explanation (less indexing)
    @MapsId
    private ChosenQuestions chosenQuestions;

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
