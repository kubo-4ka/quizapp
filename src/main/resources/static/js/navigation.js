// =========================
// キーボードショートカットの追加
// =========================
document.addEventListener("keydown", function(event) {
    if (event.altKey) { // Altキーが押されている場合
        switch (event.key.toLowerCase()) {
            case "n": // Alt + N → 次の問題へ
                document.getElementById("nextQuestionBtn")?.click();
                event.preventDefault();
                break;
            case "p": // Alt + P → 前の問題へ
                document.getElementById("previousQuestionForm")?.submit();
                event.preventDefault();
                break;
            case "s": // Alt + S → 採点画面へ
                document.getElementById("confirmScoringBtn")?.click();
                event.preventDefault();
                break;
        }
    } else if (event.key === "Enter") {
        // Enterキーで回答を送信（選択肢がある場合のみ）
        let selectedRadio = document.querySelector('input[name="selectedChoiceId"]:checked');
        let selectedCheckbox = document.querySelector('input[name="selectedChoiceIdMultipleChoiceList[]"]:checked');
        if (selectedRadio || selectedCheckbox) {
            document.getElementById("quizForm")?.submit();
            event.preventDefault();
        }
    }
});

// =========================
// スワイプ操作の追加（スマホ用）
// =========================
let touchStartX = 0;
let touchStartY = 0;
let touchEndX = 0;
let touchEndY = 0;

document.addEventListener("touchstart", function(event) {
    touchStartX = event.touches[0].clientX;
    touchStartY = event.touches[0].clientY;
});

document.addEventListener("touchend", function(event) {
    touchEndX = event.changedTouches[0].clientX;
    touchEndY = event.changedTouches[0].clientY;
    handleSwipe();
});

function handleSwipe() {
    let swipeDistanceX = touchEndX - touchStartX;  // 左右移動量
    let swipeDistanceY = Math.abs(touchEndY - touchStartY);  // 上下移動量

    if (swipeDistanceY > 30) {
        // 縦移動が大きい場合はスワイプと判定せず、スクロール操作とみなす
        return;
    }

    if (swipeDistanceX > 50) {
        // 右スワイプ → 前の問題へ
        document.getElementById("previousQuestionForm")?.submit();
    } else if (swipeDistanceX < -50) {
        // 左スワイプ → 次の問題へ
        document.getElementById("nextQuestionBtn")?.click();
    }
}

// =========================
// ダブルタップで次へ進む
// =========================
let lastTap = 0;

document.addEventListener("touchend", function(event) {
    let currentTime = new Date().getTime();
    let tapLength = currentTime - lastTap;
    
    if (tapLength < 300 && tapLength > 0) { 
        // ダブルタップを検出（300ms以内の連続タップ）
        document.getElementById("nextQuestionBtn")?.click();
    }

    lastTap = currentTime;
});
