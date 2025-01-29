var quizForm = document.getElementById('quizForm');
quizForm.addEventListener('submit', function(event) {
	event.preventDefault(); // ページ遷移を防ぐ

	// 単一選択肢の取得
	var selectedChoiceId = document.querySelector('input[name="selectedChoiceId"]:checked');

	// 複数選択肢の取得
	var selectedChoiceIdMultipleChoiceList = Array.from(document.querySelectorAll('input[name="selectedChoiceIdMultipleChoiceList[]"]:checked'))
		.map(checkbox => checkbox.value);

	// 正解選択肢の取得
	var correctChoiceIds = document.getElementById('correctChoiceIds').value.split(',');
	console.log("Correct Choice IDs:", correctChoiceIds);

	// 解説の表示処理と正解選択肢のハイライトをまとめる
	if ((selectedChoiceId && selectedChoiceId.value) || selectedChoiceIdMultipleChoiceList.length > 0) {
		// 解説を表示
		var explanation = document.getElementById('explanation');
		explanation.style.display = 'block'; // 解説を表示
		console.log("explanation.style.display: " + explanation.style.display);

		// 正解選択肢をハイライト
		correctChoiceIds.forEach(function(id) {
			var label = document.querySelector(`label[for='choice-${id}']`);
			console.log(`Searching for label with ID choice-${id}`);
			console.log("label found:", label);
			if (label) {
				label.classList.add('correct-choice'); // CSSクラスを追加
			}
		});
	} else {
		console.log("No choices selected. Explanation and highlights will not be displayed.");
	}
});
