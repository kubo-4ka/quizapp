package com.myapp.quizapp.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Question;

@Service
public class QuizScoringImpl implements QuizScoring {

	private static final Logger logger = LogManager.getLogger(QuizScoringImpl.class);

	@Override
	public double scoreQuiz(List<Question> randomQuestions, List<Integer> selectedChoiceIds) {
		logger.error("★★★scoreQuiz Start");
		double score = 0.0;

		// 質問リストと回答IDリストのサイズが同じであることを確認
		logger.error("★★randomQuestions.size() : " + randomQuestions.size());
		logger.error("★★selectedChoiceIds.size() : " + selectedChoiceIds.size());
		if (randomQuestions.size() == selectedChoiceIds.size()) {
			// 各質問について正解判定を行い、スコアを計算
			for (int i = 0; i < randomQuestions.size(); i++) {
				Question question = randomQuestions.get(i);
				Integer selectedChoiceId = selectedChoiceIds.get(i);
				logger.error("★★★★selectedChoiceIds.get(i) : " + selectedChoiceId);
				logger.error("★★★★correctAnswerId : " + question.getCorrectAnswerId());

				// 正解判定を行い、正解であればスコアを増加
				if (isCorrectAnswer(question, selectedChoiceId)) {
					score += 1.0;
				}
			}
		} else {
			// サイズが異なる場合はエラーとして処理するか、例外を投げる
			throw new IllegalArgumentException("質問リストと回答IDリストのサイズが一致しません。");
		}

		return score;
	}

	@Override
	public boolean isCorrectAnswer(Question question, Integer selectedChoiceId) {
		// 単一選択問題の正解判定ロジック
		if (selectedChoiceId == null) {
			return false; // null の場合は不正解
		}
		// 基本データ型 `int` と `Integer` の比較
		logger.error("★★★★question.getCorrectAnswerId() : " + question.getCorrectAnswerId());
		logger.error("★★★★selectedChoiceId              : " + selectedChoiceId);
		logger.error("★★★★question.getCorrectAnswerId().getClass().getName() : " + question.getCorrectAnswerId().getClass().getName());
		logger.error("★★★★selectedChoiceId.getClass().getName()              : " + selectedChoiceId.getClass().getName());
		return question.getCorrectAnswerId().equals(selectedChoiceId);
	}

	@Override
	public boolean isCorrectAnswer(Question question, List<Integer> selectedChoiceIds) {
		// 複数選択問題の正解判定ロジック
		if (selectedChoiceIds == null || selectedChoiceIds.isEmpty()) {
			return false; // 空のリストやnull の場合は不正解
		}
		// 選択されたIDのリストが正解の選択肢IDリストと完全に一致するかをチェック
		return question.getCorrectAnswerIds().containsAll(selectedChoiceIds) &&
			   selectedChoiceIds.containsAll(question.getCorrectAnswerIds());
	}
}
