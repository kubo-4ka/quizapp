// テーマ切り替え用のイベントリスナー
//document.querySelectorAll('.dropdown-item').forEach(function (item) {
//document.querySelectorAll('.navbar-toggler').forEach(function(item) {
document.querySelectorAll('.dropdown-item').forEach(function(item) {
	item.addEventListener('click', function() {
		console.log('ハンバーガーメニューがクリックされました。');
		var selectedTheme = this.getAttribute('data-theme');
		console.log("selectedTheme ;" + selectedTheme);
		// テーマ切り替えの処理を実行
		switchTheme(selectedTheme);
	});
});

// テーマ切り替え関数
function switchTheme(themeName) {
    var linkElement = document.getElementById('themeStylesheet');
    var themeFilePath = '/css/' + themeName + '.css'; // ローカル相対パスを構築
	console.log("themeFilePath ;" + themeFilePath);
	linkElement.href = themeFilePath;
	console.log("linkElement.href ;" + linkElement.href);
}
