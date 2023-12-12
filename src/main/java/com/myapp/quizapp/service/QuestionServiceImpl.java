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

	@Autowired
	public QuestionServiceImpl(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
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

//    @Override
//    public List<Question> getRandomQuestions(int count) {
//        return questionRepository.findRandomQuestions(count);
//    }
    @Override
    public List<Question> getRandomQuestions(int count) {
        // ランダムな質問を取得するロジックを実装
        // ここでは例として、単純にすべての質問を取得してシャッフルしていますが、
        // 実際のプロダクションコードではデータベースやランダムサンプリングのための適切なクエリを使用することをお勧めします。
        List<Question> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions);

        // countが実際の質問数より大きい場合は、すべての質問を返すことも考えられますが、
        // ここではcountに指定された数だけ取得するようにしています。
        return allQuestions.subList(0, Math.min(count, allQuestions.size()));
    }
    
    @Override
    public List<Question> getAllRandomQuestions() {
        return questionRepository.findAllRandomQuestions();
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
