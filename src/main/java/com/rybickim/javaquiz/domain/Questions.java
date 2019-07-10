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
    @OneToOne(fetch = FetchType.LAZY) //read vlad mihalcea for an explanation (less indexing)
    @MapsId
    private Categories categories;

    public Questions(String question){
        this.question = question;
    }
}
