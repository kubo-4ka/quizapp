package com.myapp.quizapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String choiceText;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

//    // getQuestionId() メソッド
//    public int getQuestionId() {
//        return question.getId();
//    }
//
//    // setQuestionId(int) メソッド
//    public void setQuestionId(int questionId) {
//        this.question.id = questionId;
//    }
}
