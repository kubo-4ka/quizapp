// サーバーから取得した選択肢のリスト (仮のデータ)
var choicesData = ["選択肢1", "選択肢2", "選択肢3", "選択肢4"];

// 選択肢を動的に生成し、HTMLに追加
var choicesContainer = document.getElementById("choices");
for (var i = 0; i < choicesData.length; i++) {
    var choice = choicesData[i];

    var radioInput = document.createElement("input");
    radioInput.type = "radio";
    radioInput.name = "choice"; // 選択肢の名前
    radioInput.value = choice; // 選択肢の値

    var label = document.createElement("label");
    label.appendChild(radioInput);
    label.appendChild(document.createTextNode(choice));

    choicesContainer.appendChild(label);
}
