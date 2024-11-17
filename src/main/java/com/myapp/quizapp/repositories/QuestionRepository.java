package com.myapp.quizapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myapp.quizapp.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	// 任意のカスタムクエリメソッドを追加できます

	// ランダムに指定数の質問を取得するクエリ
	@Query(value = "SELECT * FROM question ORDER BY RAND() LIMIT :count", nativeQuery = true)
	List<Question> findRandomQuestions(@Param("count") int count);

	// ランダムに全ての質問を取得するクエリ
	@Query(value = "SELECT * FROM question ORDER BY RAND()", nativeQuery = true)
	List<Question> findAllRandomQuestions();

	// Spring Data JPA が自動的に生成するクエリメソッドで、Question エンティティ内の themeId に基づいて質問を取得
	List<Question> findByThemeId(long themeId);
	
	// JPAのクエリメソッドを使用して、テーマIDに関連付けられた質問の数を取得
    Long countByThemeId(long themeId);
}
