-- テーマの挿入
INSERT INTO theme (id, name, description) VALUES
(7, 'アニメ・漫画クイズ', '人気アニメや漫画のトリビアを試すクイズです。'),
(8, 'オリンピッククイズ', 'オリンピックの歴史や競技に関する知識を試します。'),
(9, '音楽クイズ', '音楽に関する世界のトリビアを解きましょう。'),
(10, '料理クイズ', '世界の料理や食文化に関する知識を試すクイズです。');

-- アニメ・漫画クイズの質問、選択肢、解説
INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(19, 'ドラゴンボールの主人公の名前は？', 93, 'SINGLE_CHOICE', 7),
(20, 'ワンピースの海賊団の名前を3つ選んでください。', NULL, 'MULTIPLE_CHOICE', 7);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(93, 19, '孫悟空', true),
(94, 19, 'ナルト', false),
(95, 19, 'ルフィ', false),
(96, 19, '一護', false),
(97, 20, '麦わらの一味', true),
(98, 20, '赤髪海賊団', true),
(99, 20, '黒ひげ海賊団', true),
(100, 20, '銀魂団', false),
(101, 20, '藍染隊', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(19, 19, 'ドラゴンボールの主人公は孫悟空です。'),
(20, 20, 'ワンピースには「麦わらの一味」、「赤髪海賊団」、「黒ひげ海賊団」などの海賊団が登場します。');

-- オリンピッククイズの質問、選択肢、解説
INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(21, 'オリンピックが最初に開催された都市は？', 102, 'SINGLE_CHOICE', 8),
(22, '陸上競技で使用されるトラックの距離は何メートルか？', 106, 'SINGLE_CHOICE', 8);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(102, 21, 'アテネ', true),
(103, 21, 'ローマ', false),
(104, 21, 'パリ', false),
(105, 21, 'ロンドン', false),
(106, 22, '400メートル', true),
(107, 22, '800メートル', false),
(108, 22, '100メートル', false),
(109, 22, '200メートル', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(21, 21, 'オリンピックは最初にアテネで開催されました。'),
(22, 22, '陸上競技のトラックは一般的に400メートルの距離があります。');

-- 音楽クイズの質問、選択肢、解説
INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(23, 'ビートルズの出身地はどこですか？', 110, 'SINGLE_CHOICE', 9),
(24, '次のうち、クラシック音楽の作曲家を3人選んでください。', NULL, 'MULTIPLE_CHOICE', 9);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(110, 23, 'リバプール', true),
(111, 23, 'ロンドン', false),
(112, 23, 'マンチェスター', false),
(113, 23, 'バーミンガム', false),
(114, 24, 'バッハ', true),
(115, 24, 'モーツァルト', true),
(116, 24, 'ベートーヴェン', true),
(117, 24, 'マイケル・ジャクソン', false),
(118, 24, 'レディー・ガガ', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(23, 23, 'ビートルズはイギリスのリバプールで結成されました。'),
(24, 24, 'バッハ、モーツァルト、ベートーヴェンは有名なクラシック音楽の作曲家です。');

-- 料理クイズの質問、選択肢、解説
INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(25, '日本の代表的な料理である「寿司」に欠かせない食材は？', 119, 'SINGLE_CHOICE', 10),
(26, 'イタリア料理として有名なものを2つ選んでください。', NULL, 'MULTIPLE_CHOICE', 10);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(119, 25, '米', true),
(120, 25, 'パン', false),
(121, 25, 'パスタ', false),
(122, 25, 'ジャガイモ', false),
(123, 26, 'ピザ', true),
(124, 26, 'パスタ', true),
(125, 26, 'タコス', false),
(126, 26, 'キムチ', false),
(127, 26, 'フォー', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(25, 25, '寿司は主に米を使って作られる日本の伝統料理です。'),
(26, 26, 'ピザとパスタはイタリア料理の代表的な例です。');
