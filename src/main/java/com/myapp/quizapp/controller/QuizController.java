package com.myapp.quizapp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

import com.myapp.quizapp.model.Choice;
import com.myapp.quizapp.model.Explanation;
import com.myapp.quizapp.model.Question;
import com.myapp.quizapp.service.ChoiceService;
import com.myapp.quizapp.service.ExplanationService;
import com.myapp.quizapp.service.QuestionService;
import com.myapp.quizapp.service.QuizScoring;

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

	// selectedChoiceIdsの宣言
	private List<Integer> selectedChoiceIds = new ArrayList<>();
	private int currentQuestionIndex = 0;

	@GetMapping("/quiz/set-questions")
	public String showQuizSetQuestionsPage(HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");

		// 問題数の上限を取得
		Long maxQuestionCount = questionService.getTotalQuestionCount();
		logger.error("maxQuestionCount ; " + maxQuestionCount);

		// 問題数の上限をビューに渡す
		model.addAttribute("maxQuestionCount", maxQuestionCount);

		// セッションに numOfQuestions を保存
		session.setAttribute("maxQuestionCount", maxQuestionCount);

		return "set-questions"; // set-questions.html のファイル名
	}

	@GetMapping("/set-questions")
	public String showSetQuestionsPage() {
		return "set-questions"; // set-questions.html のファイル名
	}

	@GetMapping("/quiz/start")
	public String startQuiz(@RequestParam(name = "numOfQuestions") int numOfQuestions, HttpSession session,
			Model model) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");

		// numOfQuestions には選択した問題数が含まれます
		logger.error("★★★numOfQuestions : " + numOfQuestions);

		// クイズを開始する処理
		selectedChoiceIds = new ArrayList<>(Collections.nCopies(numOfQuestions, null));
//		selectedChoiceIds = new ArrayList<>();
//		List<Integer> selectedChoiceIds = new ArrayList<>();
		// セッションに selectedChoiceIds を保存
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);

		currentQuestionIndex = 1; // 最初の質問なので1

		// ランダムな順序で質問を取得
		List<Question> randomQuestions = questionService.getRandomQuestions(numOfQuestions);
		logger.error("★★★randomQuestions.size() : " + randomQuestions.size());

		// 最初の質問を取得
		if (!randomQuestions.isEmpty()) {
			Question firstQuestion = randomQuestions.get(0);
			// 質問の情報をログに出力
			logger.error("★★★First question ID: " + firstQuestion.getId());
			logger.error("★★★First question text: " + firstQuestion.getQuestionText());
			// 他の質問の情報も必要に応じて出力

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

		logger.error("★★randomQuestions.size() : " + randomQuestions.size());
		logger.error("★★selectedChoiceIds.size() : " + selectedChoiceIds.size());

		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@GetMapping("/quiz/question/{currentQuestionIndex}")
	public String getQuestion(@PathVariable int currentQuestionIndex, HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");

		// ランダマイズされた質問リストをセッションから取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★currentQuestionIndex : " + currentQuestionIndex);

		// 現在の質問のIDに対応する質問を取得
		Question currentQuestion = randomQuestions.stream()
				.filter(q -> q.getId() == randomQuestions.get(currentQuestionIndex - 1).getId()).findFirst()
				.orElse(null);

		if (currentQuestion == null) {
			// 質問が見つからない場合のエラーログ
			logger.error("Question not found for ID: " + currentQuestionIndex);
			return "error"; // エラーページへリダイレクトまたはエラーメッセージを表示
		}

		// 解説を取得する処理
		Explanation explanation = explanationService.getExplanationByQuestionId(currentQuestion.getId());

		// 選択肢を取得する処理
		List<Choice> choices = choiceService.getChoicesByQuestionId(currentQuestion.getId());

		// 選択肢に質問を設定
		choices.forEach(c -> c.setQuestion(currentQuestion));

		// 問題と選択肢をビューに渡す
		model.addAttribute("question", currentQuestion);
		model.addAttribute("choices", choices);
		model.addAttribute("explanation", explanation);

		// セッションから numOfQuestions の値を取得
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");

		// numOfQuestions の値が取得できることを確認
		logger.error("★★numOfQuestions : " + numOfQuestions);
		logger.error("★★currentQuestionIndex : " + currentQuestionIndex);

		model.addAttribute("numOfQuestions", numOfQuestions);
		model.addAttribute("currentQuestionIndex", currentQuestionIndex);

		// セッションから回答済みの選択肢を取得
		selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");

		// セッションに選択肢が保存されていれば、それをビューに渡す
		if (selectedChoiceIds != null && !selectedChoiceIds.isEmpty()) {
			model.addAttribute("selectedChoiceIds", selectedChoiceIds);
		}

		return "quiz-questions"; // クイズ回答選択および解説表示画面のビュー名
	}

	@GetMapping("/quiz/previous-question")
	public String previousQuestion(HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★previous-question Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★★randomQuestions : " + randomQuestions);

		// セッションから選択を取得
		selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★selectedChoiceIds : " + selectedChoiceIds);

		// 選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			logger.error("★★★selectedChoiceIds == null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 今何問目かを取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
		// 今の問題に対する選択を取得
		Integer selectedChoiceId = (Integer) session.getAttribute("selectedChoiceId");

		// ビューにデータを渡す
		model.addAttribute("selectedChoiceIds", selectedChoiceIds);

		// 前の問題へ戻る処理
		if (currentQuestionIndex > 1) {
			// インデックスをデクリメント
			currentQuestionIndex--;
			session.setAttribute("currentQuestionIndex", currentQuestionIndex);

			// セッションから回答済みの選択肢を取得
			selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");

			// セッションに選択肢が保存されていれば、それをビューに渡す
			if (selectedChoiceIds != null && !selectedChoiceIds.isEmpty()) {
				model.addAttribute("selectedChoiceIds", selectedChoiceIds);
			}

			// 前の問題へリダイレクト
			return "redirect:/quiz/question/" + currentQuestionIndex;
		}

		// 最初の問題にいる場合は、そのまま表示
		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@GetMapping("/quiz/next-question")
	public String nextQuestion(HttpSession session) {
		Logger logger = LogManager.getLogger();
		logger.trace("Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");

		// セッションから選択を取得
		selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("selectedChoiceIds : " + selectedChoiceIds);

		// 選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			selectedChoiceIds = new ArrayList<>();
		}

		// 今の問題に対する選択を取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
		Integer selectedChoiceId = (Integer) session.getAttribute("selectedChoiceId");

		// セッションに選択リストを保存
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);

		// セッションから numOfQuestions の値を取得
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");

		// 次の質問に進む処理
		if (currentQuestionIndex < numOfQuestions) {
			// インデックスをインクリメント
			currentQuestionIndex++;
			session.setAttribute("currentQuestionIndex", currentQuestionIndex);

			// 次の問題へリダイレクト
			return "redirect:/quiz/question/" + currentQuestionIndex;
		} else {
			// 最後の問題にいる場合は、結果ページへリダイレクト
			return "confirm-scoring";
		}
	}

	@GetMapping("/quiz/question/last")
	public String getLastQuestion(HttpSession session, Model model) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★last-question Start");

		// セッションからランダムな質問を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		logger.error("★★★randomQuestions : " + randomQuestions);

		// セッションから選択を取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★selectedChoiceIds : " + selectedChoiceIds);

		// 選択が保存されていない場合、新しいリストを作成
		if (selectedChoiceIds == null) {
			logger.error("★★★selectedChoiceIds == null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 今何問目かを取得
		Integer currentQuestionIndex = (Integer) session.getAttribute("currentQuestionIndex");
		// 今の問題に対する選択を取得
		Integer selectedChoiceId = (Integer) session.getAttribute("selectedChoiceId");

		// セッションに選択リストを保存
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);

		// ビューにデータを渡す
		model.addAttribute("selectedChoiceIds", selectedChoiceIds);

		// 最後の問題にリダイレクト
		return "redirect:/quiz/question/" + currentQuestionIndex;
	}

	@PostMapping("/quiz/answer")
	@ResponseBody
	public ResponseEntity<String> submitAnswerAjax(@RequestParam("currentQuestionIndex") int currentQuestionIndex,
			@RequestParam(name = "selectedChoiceId", required = false) Integer selectedChoiceId, HttpSession session) {
		Logger logger = LogManager.getLogger();
		logger.error("★★★ /quiz/answer Start");
		logger.error("★★★ currentQuestionIndex : " + currentQuestionIndex);
		logger.error("★★★ selectedChoiceId : " + selectedChoiceId);

		// 選択された回答IDのリストをセッションから取得
		selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");
		logger.error("★★★ selectedChoiceIds : " + selectedChoiceIds);

		// もしリストが存在しない場合は新しく作成する
		if (selectedChoiceIds == null) {
			logger.error("★★★ selectedChoiceIds : null");
			selectedChoiceIds = new ArrayList<>();
		}

		// 選択された回答IDをリストに上書き
		int adjustedIndex = currentQuestionIndex - 1;
		int answerToSet = (selectedChoiceId != null) ? selectedChoiceId : 0;
		if (adjustedIndex >= 0 && adjustedIndex < selectedChoiceIds.size()) {
			selectedChoiceIds.set(adjustedIndex, answerToSet);
		} else {
			// インデックスが範囲外の場合のエラー処理などを追加
			// 例えば、ログにエラーメッセージを記録するなど
			logger.error("Invalid index: " + currentQuestionIndex);
		}
		// セッションに selectedChoiceIds を保存
		session.setAttribute("selectedChoiceIds", selectedChoiceIds);
		logger.error("★★★ selectedChoiceIds : " + selectedChoiceIds);

		return ResponseEntity.ok("Answer submitted!");
	}

	@GetMapping("/quiz/check-answer")
	public String checkAnswer(HttpSession session, Model model) {
		// 回答を確認する処理

		Logger logger = LogManager.getLogger();
		logger.error("★★★ /quiz/check-answer Start");

		// セッションから必要な情報を取得
		List<Question> randomQuestions = (List<Question>) session.getAttribute("randomQuestions");
		selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");

		// 採点を行い、採点結果をセッションに保存
		int userScore = (int) quizScoring.scoreQuiz(randomQuestions, selectedChoiceIds);
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

		// セッションから質問数を取得
		Integer numOfQuestions = (Integer) session.getAttribute("numOfQuestions");

		// 必要に応じて他の採点結果情報も取得

		// ビューに採点結果と質問数を渡す
		model.addAttribute("userScore", userScore);
		model.addAttribute("numOfQuestions", numOfQuestions);

		// セッションから回答済みの選択肢リストを取得
		List<Integer> selectedChoiceIds = (List<Integer>) session.getAttribute("selectedChoiceIds");

		// セッションに選択肢が保存されていれば、それをビューに渡す
		if (selectedChoiceIds != null && !selectedChoiceIds.isEmpty()) {
			model.addAttribute("selectedChoiceIds", selectedChoiceIds);
		}

		return "quiz-result";
	}

}
