var quizForm = document.getElementById('quizForm');
quizForm.addEventListener('submit', function(event) {
	event.preventDefault(); // ページ遷移を防ぐ
	
	// 単一選択肢の取得
	var selectedChoiceId = document.querySelector('input[name="selectedChoiceId"]:checked');

	// 複数選択肢の取得
	var selectedChoiceIdMultipleChoiceList = Array.from(document.querySelectorAll('input[name="selectedChoiceIdMultipleChoiceList[]"]:checked'))
								 .map(checkbox => checkbox.value);

	// 解説の表示処理
	if ((selectedChoiceId && selectedChoiceId.value) || selectedChoiceIdMultipleChoiceList.length > 0) {
		var explanation = document.getElementById('explanation');
		explanation.style.display = 'block'; // 解説を表示
		console.log("explanation.style.display: " + explanation.style.display);
	}
});
