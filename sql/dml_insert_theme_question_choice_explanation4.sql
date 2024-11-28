-- テーマを挿入
INSERT INTO public.theme (id, name, description, is_authentication_required)
VALUES
(11, 'Rubyプログラミング基礎', 'Rubyの基本文法、プログラムの動作を問う初心者向けクイズ', false);

-- 質問を挿入
INSERT INTO public.question (id, question_text, correct_answer_id, question_type, theme_id)
VALUES
(29, '次のコードの出力結果は何ですか？\n\n<pre><code>puts 1 + 2 * 3', 136, 'SINGLE_CHOICE', 11),
(30, '以下のコードを実行したとき、変数xの値はどうなりますか？\n\n<pre><code>x = 10\nx += 5 if x > 8</pre></code>', 141, 'SINGLE_CHOICE', 11),
(31, '次のコードで空欄に入る適切なコードを選んでください。\n\n<pre><code>(1..5).___ { |n| puts n }</pre></code>', 144, 'SINGLE_CHOICE', 11),
(32, '次のコードの戻り値は何ですか？\n\n<pre><code>[1, 2, 3].map { |n| n * 2 }</pre></code>', 148, 'SINGLE_CHOICE', 11),
(33, '以下のコードを実行するとどうなりますか？\n\n<pre><code>hash = { a: 1, b: 2 }\nhash[:c] = 3\nputs hash</pre></code>', 152, 'SINGLE_CHOICE', 11);

-- 選択肢を挿入
INSERT INTO public.choice (id, question_id, choice_text, is_correct)
VALUES
-- 質問 29
(136, 29, '9', true),
(137, 29, '7', false),
(138, 29, '3', false),
(139, 29, 'エラーになる', false),
-- 質問 30
(140, 30, '10', false),
(141, 30, '15', true),
(142, 30, '5', false),
(143, 30, 'エラーになる', false),
-- 質問 31
(144, 31, 'each', true),
(145, 31, 'map', false),
(146, 31, 'select', false),
(147, 31, 'find', false),
-- 質問 32
(148, 32, '[2, 4, 6]', true),
(149, 32, '[1, 2, 3]', false),
(150, 32, '[1, 4, 9]', false),
(151, 32, 'エラーになる', false),
-- 質問 33
(152, 33, '{:a=>1, :b=>2, :c=>3}', true),
(153, 33, '{a: 1, b: 2, c: 3}', false),
(154, 33, '{a=1, b=2, c=3}', false),
(155, 33, 'エラーになる', false);

-- 解説を挿入
INSERT INTO public.explanation (id, question_id, explanation_text)
VALUES
(29, 29, 'Rubyでは、演算子の優先順位に従い、掛け算が足し算より先に評価されます。そのため、1 + 2 * 3 は 1 + 6 となり、結果は9です。'),
(30, 30, 'x += 5 if x > 8 というコードは条件式を満たす場合に5を加算します。x = 10 は条件を満たすため、xは15になります。'),
(31, 31, 'each メソッドは範囲内の各要素を順番に処理します。したがって、(1..5).each { |n| puts n } は1から5までを出力します。'),
(32, 32, 'map メソッドはブロックの結果を新しい配列に変換します。例の場合、各要素を2倍にした新しい配列 [2, 4, 6] が返されます。'),
(33, 33, 'Rubyのハッシュはキーと値のペアを格納します。新しいキー :c に値 3 を追加すると、結果は {:a=>1, :b=>2, :c=>3} となります。');

