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
@Table(name = "categories")
public class Categories {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    private Long version;

    @Column(name = "category_name", unique = true)
    private String categoryName;

    @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questions> questions = new ArrayList<>();

    public Categories(String categoryName){
        this.categoryName = categoryName;
    }

    public void addQuestion(Questions question){
        questions.add(question);
        question.setCategories(this);
    }

    public void removeQuestion(Questions question){
        questions.remove(question);
        question.setCategories(null);
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", questions=" + questions +
                '}';
    }
}
