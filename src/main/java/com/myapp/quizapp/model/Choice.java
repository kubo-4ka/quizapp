package com.myapp.quizapp.model;

import jakarta.persistence.Column;
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

	@Column(name = "is_correct") // データベースのカラム名とフィールドをマッピング
	private boolean isCorrect; // 正解かどうかを示すフィールドを追加

	// 正解かどうかを判定するメソッド
	public boolean isCorrect() {
		return isCorrect;
	}

}
