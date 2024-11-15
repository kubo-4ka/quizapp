-- クイズの問題を表現するテーブル
CREATE TABLE question (
    id SERIAL PRIMARY KEY,
    question_text TEXT NOT NULL,
    correct_answer_id INT,
    question_type VARCHAR(255) NOT NULL DEFAULT 'SINGLE_CHOICE'
);
