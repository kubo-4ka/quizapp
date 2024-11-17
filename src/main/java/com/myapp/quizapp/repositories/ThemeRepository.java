package com.myapp.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.quizapp.model.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    // 必要に応じてカスタムクエリメソッドを追加することができます
}
