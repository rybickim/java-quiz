package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "answers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Answers {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Questions questions;

}
