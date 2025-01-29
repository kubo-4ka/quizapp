// 経過時間表示用
function formatTime(seconds) {
    let minutes = Math.floor(seconds / 60);
    let remainingSeconds = seconds % 60;
    return `${minutes}分 ${remainingSeconds}秒`;
}

// クイズ開始からの経過時間
function updateQuizElapsedTime() {
    let quizStartTimeStr = document.getElementById("quizStartTime")?.value;
    if (!quizStartTimeStr) return;

    let quizStartTime = new Date(quizStartTimeStr);
    let now = new Date();
    let elapsedSeconds = Math.floor((now - quizStartTime) / 1000);
    
    document.getElementById("quizElapsedTime").textContent = formatTime(elapsedSeconds);
}

// 設問ごとの経過時間
let questionStartTime = new Date();
function updateQuestionElapsedTime() {
    let now = new Date();
    let elapsedSeconds = Math.floor((now - questionStartTime) / 1000);
    
    document.getElementById("questionElapsedTime").textContent = formatTime(elapsedSeconds);
}

// 1秒ごとに経過時間を更新
setInterval(updateQuizElapsedTime, 1000);
setInterval(updateQuestionElapsedTime, 1000);
