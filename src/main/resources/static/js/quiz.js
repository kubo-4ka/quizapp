var quizForm = document.getElementById('quizForm');
quizForm.addEventListener('submit', function(event) {
	event.preventDefault(); // ページ遷移を防ぐ
	var selectedChoiceId = document.querySelector('input[name="selectedChoiceId"]');
	if (selectedChoiceId && selectedChoiceId.value) {
		var explanation = document.getElementById('explanation');
		explanation.style.display = 'block'; // 解説を表示
	}
});
