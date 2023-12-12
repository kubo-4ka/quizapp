document.querySelector(".navbar-brand").addEventListener("click", function(event) {
    event.stopPropagation(); // イベント伝播を停止
    var confirmation = confirm("タイトル画面に戻りますか？");
    if (confirmation) {
        window.location.href = "/quiz/title"; // タイトル画面に遷移
    }
});
