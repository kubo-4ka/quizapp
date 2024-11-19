INSERT INTO theme (id, name, description) VALUES
(4, '世界の自然遺産クイズ', '世界各地の有名な自然遺産に関する知識をテストするクイズです。');

INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(13, 'オーストラリアにある世界最大のサンゴ礁はどれですか？', 63, 'SINGLE_CHOICE', 4),
(14, 'エベレスト山がある国を2つ選んでください。', NULL, 'MULTIPLE_CHOICE', 4);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(63, 13, 'グレートバリアリーフ', true),
(64, 13, 'ナンガパルバット', false),
(65, 13, 'マチュピチュ', false),
(66, 13, 'アンコール遺跡', false),
(67, 13, 'フィヨルドランド国立公園', false),

(68, 14, 'ネパール', true),
(69, 14, 'インド', false),
(70, 14, 'チベット自治区（中国）', true),
(71, 14, 'パキスタン', false),
(72, 14, 'ブータン', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(13, 13, 'グレートバリアリーフは世界最大のサンゴ礁で、オーストラリア北東の海岸にあります。'),
(14, 14, 'エベレスト山はネパールと中国（チベット自治区）にまたがる世界最高峰です。');

INSERT INTO theme (id, name, description) VALUES
(5, '宇宙科学クイズ', '宇宙や天文学に関する基礎的な知識を試すクイズです。');

INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(15, '太陽系で最も大きな惑星はどれですか？', 73, 'SINGLE_CHOICE', 5),
(16, '地球から見える星座を3つ選んでください。', NULL, 'MULTIPLE_CHOICE', 5);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(73, 15, '木星', true),
(74, 15, '火星', false),
(75, 15, '地球', false),
(76, 15, '土星', false),
(77, 15, '金星', false),

(78, 16, 'オリオン座', true),
(79, 16, 'ケンタウルス座', true),
(80, 16, 'りゅう座', true),
(81, 16, 'カシオペア座', false),
(82, 16, 'はくちょう座', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(15, 15, '木星は太陽系で最も大きな惑星で、質量は地球の約318倍です。'),
(16, 16, 'オリオン座、ケンタウルス座、りゅう座は、地球から観測できる星座です。');

INSERT INTO theme (id, name, description) VALUES
(6, '世界の文化遺産クイズ', '世界各地の歴史的文化遺産について学びましょう。');

INSERT INTO question (id, question_text, correct_answer_id, question_type, theme_id) VALUES
(17, '中国の万里の長城は何世紀に建てられましたか？', 86, 'SINGLE_CHOICE', 6),
(18, 'イタリアにある有名な文化遺産を3つ選んでください。', NULL, 'MULTIPLE_CHOICE', 6);

INSERT INTO choice (id, question_id, choice_text, is_correct) VALUES
(83, 17, '7世紀', false),
(84, 17, '14世紀', false),
(85, 17, '5世紀', false),
(86, 17, '紀元前7世紀', true),
(87, 17, '3世紀', false),

(88, 18, 'コロッセオ', true),
(89, 18, 'ピサの斜塔', true),
(90, 18, 'システィーナ礼拝堂', true),
(91, 18, 'エッフェル塔', false),
(92, 18, 'アルハンブラ宮殿', false);

INSERT INTO explanation (id, question_id, explanation_text) VALUES
(17, 17, '万里の長城は紀元前7世紀に建設が始まり、歴史的な防御壁として知られています。'),
(18, 18, 'イタリアのコロッセオ、ピサの斜塔、システィーナ礼拝堂は、世界遺産に登録された有名な文化遺産です。');

