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
		// 単一選択肢用の選択値を取得
		var selectedChoiceId = $("input[name='selectedChoiceId']:checked").val();

		// 複数選択肢用の選択値を配列として取得
		var selectedChoiceIdMultipleChoiceList = [];
		$("input[name='selectedChoiceIdMultipleChoiceList[]']:checked").each(function() {
			selectedChoiceIdMultipleChoiceList.push($(this).val());
		});

		var currentQuestionId = $("input[name='currentQuestionId']").val();
		var currentQuestionIndex = $("input[name='currentQuestionIndex']").val();

		console.log("selectedChoiceId: " + selectedChoiceId);
		console.log("selectedChoiceIdMultipleChoiceList: " + selectedChoiceIdMultipleChoiceList);
		console.log("currentQuestionId: " + currentQuestionId);

		// バックエンドのエンドポイントにデータを送信
		$.post("/quiz/answer", {
			currentQuestionId: currentQuestionId,
			selectedChoiceId: selectedChoiceId,
			selectedChoiceIdMultipleChoiceList: selectedChoiceIdMultipleChoiceList, // 追加
			currentQuestionIndex: currentQuestionIndex
		})
			.done(function(data) {
				// サーバーからの応答を処理する
				console.log("data: " + data);

				// 応答が成功の場合、選択された値を新しい隠しフィールドにセット
				$("#selectedChoiceId").val(selectedChoiceId);

				// selectedChoiceIdMultipleChoiceList の隠しフィールドを更新または作成
				$("#selectedChoiceIdMultipleChoiceList").remove(); // 既存の要素を削除しておく
				selectedChoiceIdMultipleChoiceList.forEach(function(choiceId) {
					$('<input>').attr({
						type: 'hidden',
						name: 'selectedChoiceIdMultipleChoiceList[]',
						id: 'selectedChoiceIdMultipleChoiceList',
						value: choiceId
					}).appendTo('#quizForm');
				});

				// クライアントサイドで画面遷移を実行
				window.location.href = nextEndpoint;
			})
			.fail(function() {
				console.log("submitAnswer error");
				// エラーの場合の処理を追加（必要に応じて）
			});
	}
});
