-- 質問を挿入 (IDは27から始めています)
INSERT INTO public.question (id, question_text, correct_answer_id, question_type, theme_id)
VALUES
(27, '近代オリンピックで最も多く金メダルを獲得した選手は誰でしょうか？\n以下の選択肢の中から正しい人物を選んでください。', null, 'SINGLE_CHOICE', 8),
(28, 'オリンピックのシンボルは5つの輪から成り立っています。\nこれらは何を象徴しているのでしょうか？\n以下から正しいものを選択してください。', null, 'SINGLE_CHOICE', 8);

-- 選択肢を挿入 (選択肢IDは128から採番を続けています)
INSERT INTO public.choice (id, question_id, choice_text, is_correct)
VALUES
(128, 27, 'マイケル・フェルプス', true),
(129, 27, 'ウサイン・ボルト', false),
(130, 27, 'カール・ルイス', false),
(131, 27, 'ラリサ・ラチニナ', false),
(132, 28, '5つの大陸を表している', true),
(133, 28, 'オリンピックの創設者を表している', false),
(134, 28, '5つの主要な競技を象徴している', false),
(135, 28, 'オリンピックの精神を象徴している', false);

-- 解説を挿入 (IDは27から始めています)
INSERT INTO public.explanation (id, question_id, explanation_text)
VALUES
(27, 27, 'マイケル・フェルプスは、オリンピックで史上最多の金メダルを獲得した選手です。\n彼は水泳競技において圧倒的な成績を残しました。'),
(28, 28, 'オリンピックのシンボルである5つの輪は、5つの大陸を表しています。\n各輪は青、黄色、黒、緑、赤の色で描かれ、世界の団結を象徴しています。');
