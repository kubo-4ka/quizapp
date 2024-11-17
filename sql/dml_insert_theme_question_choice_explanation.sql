INSERT INTO theme (id, name, description) VALUES
(2, '世界の首都クイズ', '世界各国の首都を問うクイズです。多くの選択肢から正しい首都を選ぶ問題で、地理知識を試すことができます。');

INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(9, 'フランスの首都はどこですか？', 43, 'SINGLE_CHOICE', 2),
(10, 'オーストラリアの首都はどこですか？', 48, 'SINGLE_CHOICE', 2);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(43, 9, 'パリ', true),
(44, 9, 'マルセイユ', false),
(45, 9, 'リヨン', false),
(46, 9, 'ニース', false),
(47, 9, 'ボルドー', false),

(48, 10, 'キャンベラ', true),
(49, 10, 'シドニー', false),
(50, 10, 'メルボルン', false),
(51, 10, 'ブリスベン', false),
(52, 10, 'パース', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(9, 9, 'パリはフランスの首都で、エッフェル塔が有名です。'),
(10, 10, 'キャンベラはオーストラリアの首都で、政治の中心地です。');

INSERT INTO theme (id, name, description) VALUES
(3, '日本の歴史クイズ', '日本の歴史に関するクイズです。時代ごとの出来事や人物を問う問題で、択一や択複の問題が混ざっています。');

INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(11, '徳川幕府が設立されたのは何年ですか？', 53, 'SINGLE_CHOICE', 3),
(12, '織田信長が行った有名な戦いはどれですか？', 58, 'SINGLE_CHOICE', 3);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(53, 11, '1603年', true),
(54, 11, '1598年', false),
(55, 11, '1573年', false),
(56, 11, '1615年', false),
(57, 11, '1582年', false),

(58, 12, '桶狭間の戦い', true),
(59, 12, '関ヶ原の戦い', false),
(60, 12, '大阪の陣', false),
(61, 12, '長篠の戦い', false),
(62, 12, '小田原攻め', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(11, 11, '徳川幕府は1603年に設立され、約260年続きました。'),
(12, 12, '桶狭間の戦いは織田信長が今川義元を破った有名な戦いです。');
