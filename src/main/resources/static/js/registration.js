function checkInput(input) {
	if (input.value.length > 2) {
		return removeErrorMessage(input);
	} else {
		return setErrorMessage(input);
	}
}

function checkInputPassword(input) {
	if (input.value.length >= 6) {
		return removeErrorMessage(input);
	} else {
		return setErrorMessage(input);
	}
}
function checkInputEmail(input) {
	if (input.value.length >= 5 && validateEmail(input.value)) {
		return removeErrorMessage(input);
	} else {
		return setErrorMessage(input);
	}
}

function setErrorMessage(input) {
	$(input).addClass('bg-danger-subtle');
	$(input).removeClass('bg-none is-valid');
	$(input).prev().children('.errorMessage').removeAttr('hidden');
	$(input).prev().children('.isPresent').attr('hidden', true);
	return false;
}

function removeErrorMessage(input) {
	$(input).addClass('bg-none is-valid');
	$(input).removeClass('bg-danger-subtle');
	$(input).prev().children('.errorMessage').attr('hidden', true);
	return true;
}

const validateEmail = (email) => {
	return String(email)
		.toLowerCase()
		.match(
			/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
		);
};

function editUserPhoto() {
	$('#user_photo input').click();
}
function savePreviewPhoto(input) {
	const [file] = input.files;
	if (file) {
		$('form img').attr('src', URL.createObjectURL(file));
	}
}

async function isUserAlreadyRegistered(email) {
	let isPresent = true;
	await $.get("isUserPresent", { email: email }, function(data) {
		if (data === true) {
			setErrorMessage($("input[name='email']"));
			$("input[name='email']").prev().children('.isPresent').removeAttr('hidden');
			$(input).prev().children('.errorMessage').attr('hidden', true);
			isPresent = true;
		} else {
			removeErrorMessage($("input[name='email']"));
			$("input[name='email']").prev().children('.isPresent').attr('hidden', true);
			isPresent = false;
		}
	})
	return Promise.resolve(isPresent);
}

async function checkForm() {
	var firstname = $("input[name='firstname']")[0];
	var lastname = $("input[name='lastname']")[0];
	var email = $("input[name='email']")[0];
	var password = $("input[name='password']")[0];

	const isPresent = await isUserAlreadyRegistered(email.value);
	if (!isPresent && checkInput(firstname) && checkInput(lastname) && checkInputEmail(email) && checkInputPassword(password)) {
		const form = $("#userform")[0];
		var formData = new FormData(form);
		$.ajax({
			type: "POST",
			url: 'registration',
			data: formData,
			contentType: false,
			processData: false,
			complete: function(data) {
				window.location = data.responseText;
			}
		});
	}
	else {
		if (!checkInput(firstname))
			$("input[name='firstname']").attr("onkeyup", "checkInput(this)");
		if (!checkInput(lastname))
			$("input[name='lastname']").attr("onkeyup", "checkInput(this)");
		if (!checkInputEmail(email))
			$("input[name='email']").attr("onkeyup", "checkInputEmail(this)");
		if (!checkInputPassword(password))
			$("input[name='password']").attr("onkeyup", "checkInputPassword(this)");

	}
}
