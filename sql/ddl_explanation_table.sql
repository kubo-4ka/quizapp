-- クイズの解説を表現するテーブル
CREATE TABLE explanation (
    id SERIAL PRIMARY KEY,
    question_id INT REFERENCES question(id),
    explanation_text TEXT NOT NULL
);