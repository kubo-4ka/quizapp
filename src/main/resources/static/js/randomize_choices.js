// ページ読み込み後に実行
document.addEventListener("DOMContentLoaded", function () {
    // 選択肢をランダムに並び替える
    var choicesContainer = document.getElementById("choices");
    var choices = Array.from(choicesContainer.children);

    choices.sort(function () {
        return 0.5 - Math.random();
    });

    choices.forEach(function (choice) {
        choicesContainer.appendChild(choice);
    });
});
