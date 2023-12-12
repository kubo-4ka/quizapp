$(document).ready(function() {
	// "前に戻る" ボタンがクリックされたときの処理
	$("#previousQuestionForm").on("click", function(event) {
		event.preventDefault();
		submitQuizForm("/quiz/previous-question");
	});

	// "次へ進む" ボタンがクリックされたときの処理
	$("#nextQuestionBtn").on("click", function(event) {
		event.preventDefault();
		submitQuizForm("/quiz/next-question");
	});

	// サーバーに回答を送信する関数
	function submitQuizForm(nextEndpoint) {
		var selectedChoiceId = $("input[name='selectedChoiceId']:checked").val();
		var currentQuestionId = $("input[name='currentQuestionId']").val();
		var currentQuestionIndex = $("input[name='currentQuestionIndex']").val(); // 追加
		console.log("selectedChoiceId: " + selectedChoiceId);
		console.log("currentQuestionId: " + currentQuestionId);

		// バックエンドのエンドポイントにデータを送信
		$.post("/quiz/answer", {
			currentQuestionId: currentQuestionId,
			selectedChoiceId: selectedChoiceId,
			currentQuestionIndex: currentQuestionIndex // 追加
		})
			.done(function(data) {
				// サーバーからの応答を処理する
				console.log("data: " + data);

				// 応答が成功の場合、選択された値を新しい隠しフィールドにセット
				$("#selectedChoiceId").val(selectedChoiceId);

				// クライアントサイドで画面遷移を実行
				window.location.href = nextEndpoint;
			})
			.fail(function() {
				console.log("submitAnswer error");
				// エラーの場合の処理を追加（必要に応じて）
			});
	}
});
