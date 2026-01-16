function showConfirm() {
	const mailLink = document.getElementById('mailLink');
	mailLink.addEventListener('click', function(e) {
		if (!window.confirm('Please note that I do not reply to emails that are not related to this website. Is that acceptable?')) {
			e.preventDefault();
		}
	}, {
		'once': true
	});
}

document.addEventListener('DOMContentLoaded', showConfirm);