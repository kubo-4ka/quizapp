-- クイズの問題を表現するテーブル
CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    question_text TEXT NOT NULL,
    correct_answer_id INT
);
