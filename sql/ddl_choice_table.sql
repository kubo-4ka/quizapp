-- クイズの選択肢を表現するテーブル
CREATE TABLE choice (
    id SERIAL PRIMARY KEY,
    question_id INT REFERENCES question(id),
    choice_text TEXT NOT NULL,
    is_correct BOOLEAN
);
