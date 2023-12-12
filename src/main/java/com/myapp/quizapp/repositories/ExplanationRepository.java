package com.myapp.quizapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.quizapp.model.Explanation;

@Repository
public interface ExplanationRepository extends JpaRepository<Explanation, Long> {
    Explanation findByQuestionId(long questionId);

	void deleteByQuestionId(long questionId);
}
