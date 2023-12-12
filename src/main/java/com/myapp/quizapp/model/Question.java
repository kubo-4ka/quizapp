package com.myapp.quizapp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id // これが識別子フィールドを示します
    @GeneratedValue(strategy = GenerationType.IDENTITY) int id;
    private String questionText;
    private int correctAnswerId;
    
    @OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
    private Explanation explanation;

 // Question エンティティ
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Choice> choices;
}
