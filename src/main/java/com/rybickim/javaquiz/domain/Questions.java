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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String question;
    @OneToOne //read vlad mihalcea
    @Column(name = "category_id")
    private Long categoryId;

    public Questions(String question){
        this.question = question;
    }
}
