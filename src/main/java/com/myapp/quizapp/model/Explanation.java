package com.myapp.quizapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Explanation {
    @Id // これが識別子フィールドを示します
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    private int questionId;
    private String explanationText;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
    
}
