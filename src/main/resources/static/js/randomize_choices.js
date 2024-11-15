// ページ読み込み後に実行
document.addEventListener("DOMContentLoaded", function () {
    var choicesContainer = document.getElementById("choices");
    var choices = Array.from(choicesContainer.querySelectorAll("input[type='checkbox']"));

	// シングル選択肢と複数選択肢を区別して取得
	var singleChoiceElements = choicesContainer.querySelectorAll('div > input[type="radio"]');
	var multipleChoiceElements = choicesContainer.querySelectorAll('div > input[type="checkbox"]');

	var choices = [];
	if (singleChoiceElements.length > 0) {
		choices = Array.from(singleChoiceElements).map(input => input.parentElement);
	} else if (multipleChoiceElements.length > 0) {
		choices = Array.from(multipleChoiceElements).map(input => input.parentElement);
	}

	// 並べ替えが必要な場合のみ実行
	if (choices.length > 0) {
		choices.sort(function () {
			return 0.5 - Math.random();
		});
		choices.forEach(function (choice) {
			choicesContainer.appendChild(choice);
		});
	}
    // 以下調査用

	// 要素の確認を正しく行う
    // choicesContainer が正しく取得できているか確認
    if (!choicesContainer) {
        console.error("Error: choicesContainer が見つかりませんでした。");
        return;
    }

	// console.log("choices:", choices);
	// console.log("choicesContainer:", choicesContainer);

    // hidden フィールドから selectedChoiceIdMultipleChoiceList の値を取得
	var isSingleChoice = $("input[name='isSingleChoice']").val();
	console.log("isSingleChoice: " + isSingleChoice);

	var selectedChoiceIdMultipleChoiceListString= $("input[name='selectedChoiceIdMultipleChoiceListString']").val();
	console.log("selectedChoiceIdMultipleChoiceListString: " + selectedChoiceIdMultipleChoiceListString);

	var selectedChoiceIdMultipleChoiceList= $("input[name='selectedChoiceIdMultipleChoiceList']").val();
	console.log("selectedChoiceIdMultipleChoiceList: " + selectedChoiceIdMultipleChoiceList);

    var selectedChoiceIdMultipleChoiceListValue = document.getElementById("selectedChoiceIdMultipleChoiceListValue").value;
    var selectedChoiceIdMultipleChoiceList = selectedChoiceIdMultipleChoiceListValue ? selectedChoiceIdMultipleChoiceListValue.split(',').map(Number) : [];

/*    console.log("selectedChoiceIdMultipleChoiceListValue: " + selectedChoiceIdMultipleChoiceListValue);
    console.log("selectedChoiceIdMultipleChoiceList: " + selectedChoiceIdMultipleChoiceList);
*/
	var selectedChoiceIdMultipleChoiceListValue = $("input[name='selectedChoiceIdMultipleChoiceListValue']").val();
/*	console.log("selectedChoiceIdMultipleChoiceListValue: " + selectedChoiceIdMultipleChoiceListValue);
*/

    // choicesContainer 内の各選択肢の ID を出力    
    choices.forEach(function (choice) {
        console.log("choice.id: " + choice.value);

        // choice.id が selectedChoiceIdMultipleChoiceList に含まれているか確認
        if (selectedChoiceIdMultipleChoiceList.includes(parseInt(choice.value))) {
            console.log("Matched: " + choice.value);
        }
    });

});
