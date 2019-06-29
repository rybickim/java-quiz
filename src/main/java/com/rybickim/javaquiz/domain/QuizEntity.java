package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
public class QuizEntity {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String question;
    @Column()
    @Lob
    private String answer;

    public QuizEntity(String question, String answer){
        this.question = question;
        this.answer = answer;
    }
}
