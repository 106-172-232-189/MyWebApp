function showConfirm() {
	const mailLink = document.getElementById('mailLink');
	mailLink.addEventListener('click', function(e) {
		if (!window.confirm('このリンクは当サイトに関する連絡のためのリンクです。私の活動と関係の無い内容のメールには返信致しません。よろしいでしょうか？\nhttps://twitter.com/RbSbH9WTaKkBtGd/status/1797987419826037070')) {
			e.preventDefault();
		}
	}, {
		'once': true
	});
}

document.addEventListener('DOMContentLoaded', showConfirm);