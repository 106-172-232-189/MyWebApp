function ex(isChecked) {
	const target = document.querySelectorAll(".exclusive");

	for (let i in target) {
		target[i].disabled = isChecked;
	}
}

function nex(isChecked) {
	const target = document.querySelectorAll(".notExclusive");

	for (let i in target) {
		target[i].disabled = isChecked;
	}
}