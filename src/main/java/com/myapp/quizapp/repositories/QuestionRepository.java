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
}
