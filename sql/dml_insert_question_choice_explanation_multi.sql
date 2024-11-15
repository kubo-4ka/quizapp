-- Insert into question table
INSERT INTO question (id, question_text, correct_answer_id, question_type) VALUES
(7, '次のうち、政令指定都市を1つ選んでください。', 33, 'SINGLE_CHOICE'),
(8, '次のうち、政令指定都市を3つ選んでください。', NULL, 'MULTIPLE_CHOICE');

-- Insert into choice table
INSERT INTO choice (id, choice_text, question_id, is_correct) VALUES
-- Question 1: 択一
(31, '浜松市', 7, false),
(32, '京都市', 7, false),
(33, '札幌市', 7, true),
(34, '岡山市', 7, false),
(35, '相模原市', 7, false),
(36, '高松市', 7, false),

-- Question 2: 択複
(37, '札幌市', 8, true),
(38, 'さいたま市', 8, true),
(39, '横浜市', 8, true),
(40, '宇都宮市', 8, false),
(41, '川越市', 8, false),
(42, '松山市', 8, false);

-- Insert into explanation table
INSERT INTO explanation (id, explanation_text, question_id) VALUES
(7, '政令指定都市は全国に20市あります。札幌市はそのうちの一つです。', 7),
(8, '政令指定都市は全国に20市あり、札幌市、さいたま市、横浜市はその例です。', 8);
