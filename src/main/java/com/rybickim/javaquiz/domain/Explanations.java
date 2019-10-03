package com.rybickim.javaquiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Indexed
@Entity
@Data
@NoArgsConstructor
@Table(name = "explanations")
public class Explanations {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Questions questions;

    @Lob
    @Column(name = "explanation_text")
    private String explanationText;

    @Column(name = "explanation_diagram_file_id")
    private String explanationDiagramFileId;
}
