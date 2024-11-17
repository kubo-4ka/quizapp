package com.myapp.quizapp.model;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	@Id // これが識別子フィールドを示します
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String questionText;
//	private int correctAnswerId;
    // 修正: int から Integer に変更
    private Integer correctAnswerId;
    
	// 列挙型で質問タイプを管理
	@Enumerated(EnumType.STRING)  // データベースには文字列として保存される
	private QuestionType questionType; // QuestionType は列挙型として定義

	@OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
	private Explanation explanation;

	// Question エンティティ
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Choice> choices;

	// Themeへの外部参照
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;
	
	// 追加: 複数選択問題の正解IDリストを取得するメソッド
	public List<Integer> getCorrectAnswerIds() {
		// choices リストから、正解の選択肢IDを抽出する
		return choices.stream()
					  .filter(Choice::isCorrect)  // Choice が正解であるかどうかの判定
					  .map(Choice::getId)		  // 正解の選択肢IDを取得
					  .collect(Collectors.toList());
	}

	// 質問タイプの判定メソッド
	public boolean isSingleChoice() {
		return this.questionType == QuestionType.SINGLE_CHOICE;
	}
	public boolean isMultipleChoice() {
	    return this.questionType == QuestionType.MULTIPLE_CHOICE;
	}

}
