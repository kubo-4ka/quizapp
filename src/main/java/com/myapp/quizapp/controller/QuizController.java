package com.myapp.quizapp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myapp.quizapp.model.Choice;
import com.myapp.quizapp.model.Explanation;
import com.myapp.quizapp.model.Question;
import com.myapp.quizapp.model.QuestionType; // 追加
import com.myapp.quizapp.model.Theme;
import com.myapp.quizapp.service.ChoiceService;
import com.myapp.quizapp.service.ExplanationService;
import com.myapp.quizapp.service.QuestionService;
import com.myapp.quizapp.service.QuizScoring;
import com.myapp.quizapp.service.ThemeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ChoiceService choiceService;

	@Autowired
	private ExplanationService explanationService;

	@Autowired
	private QuizScoring quizScoring;

	@Autowired
	private ThemeService themeService;

	@GetMapping("/quiz/set-questions")
	public String showQuizSetQuestionsPage(@RequestParam("themeId") long themeId, HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.error("★ ★ ★showQuizSetQuestionsPage Start");

		// テーマ情報を取得
		Theme selectedTheme = themeService.getThemeById(themeId);
		model.addAttribute("themeName", selectedTheme.getName());
		
		// テーマIDに基づいて問題数の上限を取得
		Long maxQuestionCount = questionService.getQuestionCountByThemeId(themeId);
		logger.error("★maxQuestionCount ; " + maxQuestionCount);
		logger.error("★themeId ; " + themeId);

		// 問題数の上限をビューに渡す
		model.addAttribute("maxQuestionCount", maxQuestionCount);

		// テーマIDをビューに渡す
		model.addAttribute("themeId", themeId);

		// セッションに numOfQuestions を保存
		session.setAttribute("maxQuestionCount", maxQuestionCount);

		// セッションに themeId を保存
		session.setAttribute("themeId", themeId);

		return "set-questions"; // set-questions.html のファイル名
	}

	@GetMapping("/quiz/start")
	public String startQuiz(@RequestParam(name = "numOfQuestions") int numOfQuestions,
			@RequestParam(name = "themeId", required = false, defaultValue = "0") long themeId, HttpSession session,
			Model model) {

		if (themeId == 0) {
			// デフォルトのテーマIDが0の場合に対するエラーハンドリングを実装
			throw new IllegalArgumentException("テーマIDが無効です");
		}
		Logger logger = LogManager.getLogger();
		logger.error("★startQuiz Start");

		// numOfQuestions には選択した問題数が含まれます
		logger.error("★★★numOfQuestions : " + numOfQuestions);

		// themeId には選択した問題数が含まれます
		logger.error("★★★themeId : " + themeId);

		// 択一用のリストを初期化（問題数と同じサイズで null 値を持つリスト）
		List<Integer> selectedChoiceIds = new ArrayList<>(Collections.nCopies(numOfQuestions, null));
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);

		// 択複用の二次元リストを初期化（問題数と同じサイズで空のリストを持つ二次元リスト）
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = new ArrayList<>();
		for (int i = 0; i < numOfQuestions; i++) {
			selectedChoiceIdMultipleChoiceLists.add(new ArrayList<>());
		}
		session.setAttribute("selectedChoiceIdMultipleChoiceLists", selectedChoiceIdMultipleChoiceLists);

		Integer currentQuestionIndex = 1; // 最初の質問なので1

		// ランダムな順序で質問を取得
		List<Question> randomQuestions = questionService.getRandomQuestions(numOfQuestions, themeId);
		logger.error("★★★randomQuestions.size() : " + randomQuestions.size());

		// 最初の質問を取得
		if (!randomQuestions.isEmpty()) {
			Question firstQuestion = randomQuestions.get(0);
			// 質問の情報をログに出力
			logger.error("★★★First question ID: " + firstQuestion.getId());
			logger.error("★★★First question text: " + firstQuestion.getQuestionText());

			// 質問にアクセスしてchoicesプロパティを初めてアクセス
			List<Choice> choices = firstQuestion.getChoices();
			logger.error("★★★choices: " + choices);

			// セッションにランダムな質問および最初の質問のIDを保存
			session.setAttribute("randomQuestions", randomQuestions);
			session.setAttribute("currentQuestionIndex", currentQuestionIndex); // 最初の質問なので1
			session.setAttribute("currentQuestionId", firstQuestion.getId()); // 最初の質問のID
		} else {
			// ランダムな質問が一つも取得できなかった場合のエラーログ
			logger.error("No random questions were retrieved.");
		}

		// 問題数などの情報を次の画面に渡す場合、model.addAttribute を使用
		model.addAttribute("numOfQuestions", numOfQuestions);

		// セッションに numOfQuestions を保存
		session.setAttribute("numOfQuestions", numOfQuestions);

		// セッションに themeId を保存
		session.setAttribute("themeId", themeId);
		
		logger.error("★★randomQuestions.size() : " + randomQuestions.size());
		logger.error("★★selectedChoiceIds.size() : " + selectedChoiceIds.size());
		logger.error("★★selectedChoiceIdMultipleChoiceLists.size() : " + selectedChoiceIdMultipleChoiceLists.size());

		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@GetMapping("/quiz/question/{currentQuestionIndex}")
	public String getQuestion(@PathVariable int currentQuestionIndex, HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");

		// ランダマイズされた質問リストをセッションから取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★currentQuestionIndex : " + currentQuestionIndex);

		// 現在の質問を取得
		Question currentQuestion = randomQuestions.get(currentQuestionIndex - 1);
		if (currentQuestion == null) {
			// 質問が見つからない場合のエラーログ
			logger.error("Question not found for ID: " + currentQuestionIndex);
			return "error"; // エラーページへリダイレクトまたはエラーメッセージを表示
		}

		// 解説と選択肢を取得
		Explanation explanation = explanationService.getExplanationByQuestionId(currentQuestion.getId());

		// 選択肢を取得する処理
		List<Choice> choices = choiceService.getChoicesByQuestionId(currentQuestion.getId());

		// QuestionType を取得してモデルに追加
		QuestionType questionType = currentQuestion.getQuestionType();
		model.addAttribute("questionType", questionType); // ビューに質問タイプを渡す
		// questionType の値が取得できることを確認
		logger.error("★★questionType : " + questionType);

		// 選択肢に質問を設定
		choices.forEach(c -> c.setQuestion(currentQuestion));

		// モデルに質問、選択肢、解説を追加
		model.addAttribute("question", currentQuestion);
		model.addAttribute("choices", choices);
		model.addAttribute("explanation", explanation);

		// セッションから numOfQuestions の値を取得してモデルに追加
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");

		// numOfQuestions の値が取得できることを確認
		logger.error("★★numOfQuestions : " + numOfQuestions);
		logger.error("★★currentQuestionIndex : " + currentQuestionIndex);

		model.addAttribute("numOfQuestions", numOfQuestions);
		model.addAttribute("currentQuestionIndex", currentQuestionIndex);

		// 質問タイプに応じた処理
		if (questionType == QuestionType.SINGLE_CHOICE) {
			List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
			if (selectedChoiceIds != null && !selectedChoiceIds.isEmpty()) {
				model.addAttribute("selectedChoiceIds", selectedChoiceIds);
			}
		} else if (questionType == QuestionType.MULTIPLE_CHOICE) {
			List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
					.getAttribute("selectedChoiceIdMultipleChoiceLists");
			logger.error("★★selectedChoiceIdMultipleChoiceLists : " + selectedChoiceIdMultipleChoiceLists);
			if (selectedChoiceIdMultipleChoiceLists != null && !selectedChoiceIdMultipleChoiceLists.isEmpty()) {
				model.addAttribute("selectedChoiceIdMultipleChoiceLists", selectedChoiceIdMultipleChoiceLists);
			}
		}

		return "quiz-questions"; // クイズ回答選択および解説表示画面のビュー名
	}

	@GetMapping("/quiz/previous-question")
	public String previousQuestion(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★previous-question Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★★randomQuestions : " + randomQuestions);

		// 択一用：セッションからこれまでの選択結果リストを取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★selectedChoiceIds : " + selectedChoiceIds);

		// 択一用：選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			logger.error("★★★selectedChoiceIds == null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 択複用：セッションからこれまでの選択結果リストを取得
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
				.getAttribute("selectedChoiceIdMultipleChoiceLists");
		if (selectedChoiceIdMultipleChoiceLists == null) {
			selectedChoiceIdMultipleChoiceLists = new ArrayList<>();
		}
		logger.error("★★★selectedChoiceIdMultipleChoiceLists : " + selectedChoiceIdMultipleChoiceLists);
		// 今何問目かを取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");

		// 1問目以外なら前の問題へ戻るためインデックスをデクリメント
		if (currentQuestionIndex > 1) {
			currentQuestionIndex--;
			session.setAttribute("currentQuestionIndex", currentQuestionIndex);
			logger.error("★★★★★★currentQuestionIndex : " + currentQuestionIndex);

		}

		// 次に遷移する currentQuestionIndex に対応する択複回答リストを抽出
		List<Integer> selectedChoiceIdMultipleChoiceList = new ArrayList<>();
		if (currentQuestionIndex != null && currentQuestionIndex <= selectedChoiceIdMultipleChoiceLists.size()) {
			selectedChoiceIdMultipleChoiceList = selectedChoiceIdMultipleChoiceLists.get(currentQuestionIndex - 1);
		}
		logger.error("★★★★★★currentQuestionIndex : " + currentQuestionIndex);
		logger.error("★★★★★★selectedChoiceIdMultipleChoiceList : " + selectedChoiceIdMultipleChoiceList);
		logger.error("★★★★★★selectedChoiceIds : " + selectedChoiceIds);

		// selectedChoiceIdMultipleChoiceList をカンマ区切りの文字列に変換
		String selectedChoiceIdMultipleChoiceListString = selectedChoiceIdMultipleChoiceList.stream()
				.map(String::valueOf).collect(Collectors.joining(","));
		logger.error("★★★★★★selectedChoiceIdMultipleChoiceListString : " + selectedChoiceIdMultipleChoiceListString);

		// 択複用：文字列としてビューに渡す
		redirectAttributes.addFlashAttribute("selectedChoiceIdMultipleChoiceList", selectedChoiceIdMultipleChoiceList);
		redirectAttributes.addFlashAttribute("selectedChoiceIdMultipleChoiceListString",
				selectedChoiceIdMultipleChoiceListString);
		session.setAttribute("selectedChoiceIdMultipleChoiceListString", selectedChoiceIdMultipleChoiceListString);
//		model.addAttribute("testlst", selectedChoiceIdMultipleChoiceList);
//		model.addAttribute("teststr", "aaa");
//		redirectAttributes.addFlashAttribute("testlst", selectedChoiceIdMultipleChoiceList);
//		redirectAttributes.addFlashAttribute("teststr", "bbb");
//		model.addAttribute("testids", selectedChoiceIds);

		// 択一用：ビューにデータを渡す
//		model.addAttribute("selectedChoiceIds", selectedChoiceIds);

		// 前の問題へリダイレクト。最初の問題にいる場合は、そのまま表示
		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@GetMapping("/quiz/next-question")
	public String nextQuestion(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★next-question Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");

		// 択一用：セッションからこれまでの選択結果リストを取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★selectedChoiceIds : " + selectedChoiceIds);

		// 択一用：選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			logger.error("★★★selectedChoiceIds == null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 択複用：セッションからこれまでの選択結果リストを取得
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
				.getAttribute("selectedChoiceIdMultipleChoiceLists");
		if (selectedChoiceIdMultipleChoiceLists == null) {
			selectedChoiceIdMultipleChoiceLists = new ArrayList<>();
		}
		logger.error("★★★selectedChoiceIdMultipleChoiceLists : " + selectedChoiceIdMultipleChoiceLists);

		// 今何問目かを取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");

		// セッションから numOfQuestions の値を取得
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");

		// 次に遷移する currentQuestionIndex に対応する択複回答リストを抽出
		List<Integer> selectedChoiceIdMultipleChoiceList = new ArrayList<>();
		if (currentQuestionIndex != null && currentQuestionIndex < numOfQuestions) {
			selectedChoiceIdMultipleChoiceList = selectedChoiceIdMultipleChoiceLists.get(currentQuestionIndex);
		}
		logger.error("★★★★★★currentQuestionIndex : " + currentQuestionIndex);
		logger.error("★★★★★★selectedChoiceIdMultipleChoiceList : " + selectedChoiceIdMultipleChoiceList);
		logger.error("★★★★★★selectedChoiceIds : " + selectedChoiceIds);

		// selectedChoiceIdMultipleChoiceList をカンマ区切りの文字列に変換
		String selectedChoiceIdMultipleChoiceListString = selectedChoiceIdMultipleChoiceList.stream()
				.map(String::valueOf).collect(Collectors.joining(","));
		logger.error("★★★★★★selectedChoiceIdMultipleChoiceListString : " + selectedChoiceIdMultipleChoiceListString);

		// 択複用：文字列としてビューに渡す
		redirectAttributes.addFlashAttribute("selectedChoiceIdMultipleChoiceList", selectedChoiceIdMultipleChoiceList);
		redirectAttributes.addFlashAttribute("selectedChoiceIdMultipleChoiceListString",
				selectedChoiceIdMultipleChoiceListString);
		model.addAttribute("selectedChoiceIdMultipleChoiceList", selectedChoiceIdMultipleChoiceList);
		model.addAttribute("selectedChoiceIdMultipleChoiceListString", selectedChoiceIdMultipleChoiceListString);
//		model.addAttribute("teststr", "aaa");
//		redirectAttributes.addFlashAttribute("testlst", selectedChoiceIdMultipleChoiceList);
//		redirectAttributes.addFlashAttribute("teststr", "bbb");
//		model.addAttribute("testids", selectedChoiceIds);

		// セッションに選択リストを保存
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);
		session.setAttribute("selectedChoiceIdMultipleChoiceLists", selectedChoiceIdMultipleChoiceLists);
		session.setAttribute("selectedChoiceIdMultipleChoiceListString", selectedChoiceIdMultipleChoiceListString);

		// 次の質問に進む処理
		if (currentQuestionIndex < numOfQuestions) {
			// インデックスをインクリメント
			currentQuestionIndex++;
			session.setAttribute("currentQuestionIndex", currentQuestionIndex);
			logger.error("★★★★★★currentQuestionIndex : " + currentQuestionIndex);

			// 次の問題へリダイレクト
			return "redirect:/quiz/question/" + currentQuestionIndex;
		} else {
			// 最後の問題にいる場合は、確認ページへリダイレクト
			return "confirm-scoring";
		}
	}

	@GetMapping("/quiz/question/last")
	public String getLastQuestion(HttpSession session, RedirectAttributes redirectAttributes) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★★★last-question Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★★★★randomQuestions : " + randomQuestions);

		// 択一用：セッションからこれまでの選択結果リストを取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★★★selectedChoiceIds : " + selectedChoiceIds);

		// 択一用：選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			logger.error("★★★★★selectedChoiceIds == null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 択複用：セッションからこれまでの選択結果リストを取得
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
				.getAttribute("selectedChoiceIdMultipleChoiceLists");
		if (selectedChoiceIdMultipleChoiceLists == null) {
			selectedChoiceIdMultipleChoiceLists = new ArrayList<>();
		}
		logger.error("★★★★★selectedChoiceIdMultipleChoiceLists : " + selectedChoiceIdMultipleChoiceLists);
		// 今何問目かを取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");

		// 次に遷移する currentQuestionIndex に対応する択複回答リストを抽出
		List<Integer> selectedChoiceIdMultipleChoiceList = new ArrayList<>();
		if (currentQuestionIndex != null && currentQuestionIndex <= selectedChoiceIdMultipleChoiceLists.size()) {
			selectedChoiceIdMultipleChoiceList = selectedChoiceIdMultipleChoiceLists.get(currentQuestionIndex - 1);
		}
		logger.error("★★★★★currentQuestionIndex : " + currentQuestionIndex);
		logger.error("★★★★★selectedChoiceIdMultipleChoiceList : " + selectedChoiceIdMultipleChoiceList);
		logger.error("★★★★★selectedChoiceIds : " + selectedChoiceIds);

		// selectedChoiceIdMultipleChoiceList をカンマ区切りの文字列に変換
		String selectedChoiceIdMultipleChoiceListString = selectedChoiceIdMultipleChoiceList.stream()
				.map(String::valueOf).collect(Collectors.joining(","));
		logger.error("★★★★★★selectedChoiceIdMultipleChoiceListString : " + selectedChoiceIdMultipleChoiceListString);

		// 択複用：文字列としてビューに渡す
		redirectAttributes.addFlashAttribute("selectedChoiceIdMultipleChoiceListString",
				selectedChoiceIdMultipleChoiceListString);

		// 択一用：ビューにデータを渡す
		redirectAttributes.addFlashAttribute("selectedChoiceIds", selectedChoiceIds);

		// 最後の問題にリダイレクト
		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@PostMapping("/quiz/answer")
	@ResponseBody
	public ResponseEntity<String> submitAnswerAjax(@RequestParam("currentQuestionIndex") int currentQuestionIndex,
			@RequestParam(name = "selectedChoiceId", required = false) Integer selectedChoiceId,
			@RequestParam(name = "selectedChoiceIdMultipleChoiceList[]", required = false) List<String> selectedChoiceIdMultipleChoiceListStr,
			HttpSession session) {

		Logger logger = LogManager.getLogger();
		logger.error("★★★ /quiz/answer Start");
		logger.error("★★★ currentQuestionIndex : " + currentQuestionIndex);
		logger.error("★★★ selectedChoiceId : " + selectedChoiceId);
		logger.error("★★★ selectedChoiceIdMultipleChoiceList (String): " + selectedChoiceIdMultipleChoiceListStr);

		// `selectedChoiceIdMultipleChoiceListStr`を`List<Integer>`に変換
		List<Integer> selectedChoiceIdMultipleChoiceList = new ArrayList<>();
		if (selectedChoiceIdMultipleChoiceListStr != null) {
			for (String idStr : selectedChoiceIdMultipleChoiceListStr) {
				try {
					selectedChoiceIdMultipleChoiceList.add(Integer.parseInt(idStr));
				} catch (NumberFormatException e) {
					logger.error("Invalid number format for: " + idStr);
				}
			}
		}
		logger.error("★★★ selectedChoiceIdMultipleChoiceList (Integer): " + selectedChoiceIdMultipleChoiceList);

		// 択一の場合の選択された回答IDのリストをセッションから取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★ selectedChoiceIds : " + selectedChoiceIds);

		// もし択一の場合のリストが存在しない場合は新しく作成する
		if (selectedChoiceIds == null) {
			logger.error("★★★ selectedChoiceIds : null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 択複の場合セッションから既存の複数選択肢リストを取得
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
				.getAttribute("selectedChoiceIdMultipleChoiceLists");
		// 択複の場合のリストが存在しない場合は新しく作成する
		if (selectedChoiceIdMultipleChoiceLists == null) {
			selectedChoiceIdMultipleChoiceLists = new ArrayList<>();
		}

		// 配列のサイズ調整
		while (selectedChoiceIdMultipleChoiceLists.size() < currentQuestionIndex) {
			selectedChoiceIdMultipleChoiceLists.add(new ArrayList<>());
		}

		// 質問タイプを取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		Question currentQuestion = randomQuestions.get(currentQuestionIndex - 1);
		QuestionType questionType = currentQuestion.getQuestionType();
		logger.error("★★★ questionType : " + questionType);

		// 質問タイプに応じた処理
		if (questionType == QuestionType.MULTIPLE_CHOICE) {
			// 複数選択肢の場合は、`selectedChoiceIdMultipleChoiceList`を使用
			// 対象のインデックスの選択肢をクリアしてから、選択肢を設定
			selectedChoiceIdMultipleChoiceLists.set(currentQuestionIndex - 1, new ArrayList<>());
			selectedChoiceIdMultipleChoiceLists.set(currentQuestionIndex - 1, selectedChoiceIdMultipleChoiceList);
			// 択複の場合、セッションに選択肢リストを保存
			session.setAttribute("selectedChoiceIdMultipleChoiceLists", selectedChoiceIdMultipleChoiceLists);
			logger.error("★★★ updated selectedChoiceIdMultipleChoiceLists : " + selectedChoiceIdMultipleChoiceLists);
		} else if (selectedChoiceId != null) {
			// 単一選択の場合は、`selectedChoiceId`を使用してリストにセット
			while (selectedChoiceIds.size() < currentQuestionIndex) {
				// `selectedChoiceIds`のサイズが質問のインデックスに届いていない場合は拡張
				selectedChoiceIds.add(null);
			}
			selectedChoiceIds.set(currentQuestionIndex - 1, selectedChoiceId);
			// 択一の場合、セッションに選択肢のリストを保存
			session.setAttribute("selectedChoiceIds", selectedChoiceIds);
			logger.error("★★★ updated selectedChoiceIds : " + selectedChoiceIds);
		}

		return ResponseEntity.ok("Answer submitted!");
	}

	@GetMapping("/quiz/check-answer")

	public String checkAnswer(HttpSession session, Model model) {
		// 回答を確認する処理

		Logger logger = LogManager.getLogger();
		logger.error("★★★ /quiz/check-answer Start");

		// セッションから必要な情報を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		List<List<Integer>> selectedChoiceIdMultipleChoiceLists = (List<List<Integer>>) session
				.getAttribute("selectedChoiceIdMultipleChoiceLists");
		logger.error("★★★ selectedChoiceIdMultipleChoiceList.size()：" + selectedChoiceIdMultipleChoiceLists.size());
		logger.error("★★★ randomQuestions.size()：" + randomQuestions.size());

		// 採点結果を保持する変数
		int userScore = 0;

		// 質問リストをループして採点
		for (int i = 0; i < randomQuestions.size(); i++) {
			Question question = randomQuestions.get(i);
			QuestionType questionType = question.getQuestionType();
			logger.error("★★★ randomQuestions.get(i)：" + randomQuestions.get(i));
			logger.error("★★★ question.getQuestionType()：" + question.getQuestionType());

			if (questionType == QuestionType.SINGLE_CHOICE) {
				// 単一選択肢の採点
				Integer selectedChoiceId = selectedChoiceIds != null && selectedChoiceIds.size() > i
						? selectedChoiceIds.get(i)
						: null;
				if (quizScoring.isCorrectAnswer(question, selectedChoiceId)) {
					userScore++;
				}
			} else if (questionType == QuestionType.MULTIPLE_CHOICE) {
				// 複数選択肢の採点
				List<Integer> selectedChoices = selectedChoiceIdMultipleChoiceLists != null
						&& selectedChoiceIdMultipleChoiceLists.size() > i ? selectedChoiceIdMultipleChoiceLists.get(i)
								: new ArrayList<>();
				logger.error("★★★ selectedChoices：" + selectedChoices);
				if (quizScoring.isCorrectAnswer(question, selectedChoices)) {
					userScore++;
				}
			}
		}

		// 採点結果をセッションに保存
		session.setAttribute("userScore", userScore);
		logger.error("★★★ userScore : " + userScore);

		return "redirect:/quiz/quiz-result";
	}

	@GetMapping("/quiz/quiz-result")
	public String showQuizResult(HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★ /quiz/quiz-result Start");

		// セッションから採点結果を取得
		Integer userScore = (Integer) session.getAttribute("userScore");
		logger.error("★★★ userScore：" + userScore);

		// セッションから質問数を取得
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");
		logger.error("★★★ numOfQuestions：" + numOfQuestions);

		// セッションから質問数を取得
		long themeId = (long) session.getAttribute("themeId");
		logger.error("★★★ themeId：" + themeId);

		// 必要に応じて他の採点結果情報も取得

		// ビューに採点結果と質問数を渡す
		model.addAttribute("userScore", userScore);
		model.addAttribute("numOfQuestions", numOfQuestions);
		// ビューにthemeIdを渡す
		model.addAttribute("themeId", themeId);

		// セッションから回答済みの選択肢リストを取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");

		// セッションに選択肢が保存されていれば、それをビューに渡す
		if (selectedChoiceIds != null && !selectedChoiceIds.isEmpty()) {
			model.addAttribute("selectedChoiceIds", selectedChoiceIds);
		}

		return "quiz-result";
	}

}
