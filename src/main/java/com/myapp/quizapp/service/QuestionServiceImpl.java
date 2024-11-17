package com.myapp.quizapp.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.quizapp.model.Question;
import com.myapp.quizapp.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

	private final QuestionRepository questionRepository;
	private final ChoiceService choiceService;

	@Autowired
	public QuestionServiceImpl(QuestionRepository questionRepository, ChoiceService choiceService) {
		this.questionRepository = questionRepository;
		this.choiceService = choiceService;
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public Question getQuestionById(long id) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");
		logger.error("id ; " + id);
		Optional<Question> questionOptional = questionRepository.findById(id);

		if (questionOptional.isPresent()) {
			return questionOptional.get();
		} else {
			// 値が存在しない場合のエラーハンドリング
			// ログ出力
			logger.error("Question with ID {} not found", id);
			throw new NoSuchElementException("Question with ID " + id + " not found");
		}
	}

	// 新しいメソッド: テーマIDに基づいてランダムな質問を取得
	@Override
	public List<Question> getRandomQuestions(int count, long themeId) {
		// テーマIDに基づいて質問を取得
		List<Question> questionsByTheme = questionRepository.findByThemeId(themeId);
		Collections.shuffle(questionsByTheme);

		// countが質問数より大きい場合は、すべての質問を返す
		return questionsByTheme.subList(0, Math.min(count, questionsByTheme.size()));
	}

    // テーマIDに基づいて質問数を取得
    public Long getQuestionCountByThemeId(long themeId) {
        return questionRepository.countByThemeId(themeId);
    }

    @Override
	public void saveQuestion(Question question) {
		questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(long id) {
		questionRepository.deleteById(id);
	}

	// 問題の総数を取得するメソッド
	public Long getTotalQuestionCount() {
		Logger logger = LogManager.getLogger();
		logger.error("questionRepository.count() ; " + questionRepository.count());
		return questionRepository.count(); // 問題の総数を取得
	}
}
