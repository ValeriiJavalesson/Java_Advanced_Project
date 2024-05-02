$(document).ready(function() {
	$.get("allfaculty", function(data) {
		if (data !== '') {
			faculties = data;
		}
	});
	getUserApplication();
});

var faculties = '';

function getUserApplication() {
	var appl = null;
	$.get("getapplication", function(data) {
		appl = data;
	}).done(function() {
		var content = '';
		if (isEmpty(appl.faculty)) {
			content +=
				"<div class='mb-3'>You don't have application.</div><button type='button' class='btn btn-primary m-1' id='createNewApplicationButton' onclick = 'editApplication()'>Create new applicant application</button>";
		} else {
			var totalPoints = appl.certificatePoints;
			content += "<table class='table text-center'><thead><tr><th scope='col'>Faculty</th><th scope='col' class='visually-hidden'>User name</th><th scope='col'>Certificate points</th>";
			$.each(appl.subjects, function(k, v) {
				content +=
					"<th scope='col' >" + k + "</th>";
			});
			content +=
				"<th scope='col'>Total points</th><th scope='col'></th></tr></thead><tbody><tr class='table-group-divider'><th scope='row'>" +
				appl.faculty + "</th><td class='visually-hidden'>" + appl.username + "</td><td>" + appl.certificatePoints + "</td>";
			$.each(appl.subjects, function(k, v) {
				totalPoints += v;
				content +=
					"<td>" + v + "</td>";
			});
			content +=
				"<td>" + totalPoints + "</td></tr></tbody></table>";

			content += "<div><button type='button' class='btn btn-outline-secondary me-2' id='editApplicationButton' onclick = 'editApplication()'>Edit</button>" +
				"<button type='button' class='btn btn-danger ms-2' id='editApplicationButton' data-bs-toggle='modal' data-bs-target='#exampleModal'>Delete</button></div>"
		}

		$(".applicationform").html(content);
	});
};

function editApplication() {
	var appl = null;
	$.get("getapplication", function(data) {
		if (data !== '') {
			appl = data;
		}
	}).done(function() {
		var faculty = '';
		var subjects = [];
		var certificatePoints = 0;
		if (appl !== null && appl !== '') {
			faculty = appl.faculty;
			subjects = appl.subjects;
			certificatePoints = appl.certificatePoints;
		};
		content = '';
		content +=
			"<form>" +
			"<div class='mb-3'>" +
			"<label for='facultiesList' class='form-label'>Faculty</label>" +
			"<select class='form-select' id='facultiesList' onchange='getFacultySubjects()'></select>" +
			"</div >" +
			"<label for='certificatePoints' class='form-label'>Certificate Points</label>" +
			"<input type='number' class='form-control' id='certificatePoints' value=" + certificatePoints + ">" +
			"<div class='mb-3' id='applicationSubjectsList'></div>" +
			"<button type='button' class='btn btn-primary m-1' id='saveApplicationButton' onclick = 'createApplication()'>Save Application</button>" +
			"<button type='button' class='btn btn-danger m-1' id='saveApplicationButton' onclick = 'getUserApplication()'>Cancel</button>" +
			"</form >";
		$(".applicationform").html(content);
		getFacultites(faculty, subjects);
	});
};

function deleteApplication() {
	$.ajax({
		url: 'deleteApplication',
		type: 'DELETE',
		success: function(result) {
			getUserApplication();
		}
	});
}

function getFacultites(faculty, subjects) {
	var content = '';
	faculties.forEach((element, i) => {
		if (element.name === faculty)
			content += "<option selected>" + element.name + "</option>";
		else
			content += "<option>" + element.name + "</option>";
	});
	$("#facultiesList").html(content);
	getFacultySubjects(subjects);
}

function getFacultySubjects(user_subjects) {
	var subjects = null;
	faculties.forEach((element, i) => {
		var selectedFaculty = $('#facultiesList').find(":selected").text();
		if (element.name === selectedFaculty) subjects = element.subjects;
	});
	content = '';
	subjects.forEach((element) => {
		var points = null;
		if (!isEmpty(user_subjects))
			points = user_subjects[element.name];
		content +=
			"<label for='subject' class='form-label'>" + element.name + "</label>" +
			"<input type='number' class='form-control' id='subject' name='" + element.id + "' value='" + points + "'>";
	});
	$("#applicationSubjectsList").html(content);
}


function createApplication() {
	var certificatePoints = $("form input#certificatePoints").val();
	if (isEmpty(certificatePoints) || isNaN(certificatePoints)) certificatePoints = 0.0;
	var faculty = $("select#facultiesList option:selected").text();
	var subjectsNamesArray = $("div#applicationSubjectsList label").toArray();
	var subjects = {};
	subjectsNamesArray.forEach((element, i) => {
		subjects[element.innerHTML] = element.nextElementSibling.value;
		if (isEmpty(subjects[element.innerHTML]) || isNaN(subjects[element.innerHTML])) subjects[element.innerHTML] = 0.0;
	});
	var applicipantApplication = {
		certificatePoints: certificatePoints,
		faculty: faculty,
		subjects: subjects
	}
	$.ajax({
		type: "POST",
		url: 'saveapplication',
		data: JSON.stringify(applicipantApplication),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		complete: function(data) {
			if (data.responseText === 'success') {
				getUserApplication();
			} else alert("Check inputs!");
		}
	});
}

function editPhoto() {
	$('#user_photo input').click();
}

function savePhoto() {
	var user;
	var formData = new FormData();
	var image = $('#user_photo input')[0].files[0];
	formData.append('image', image);
	$.ajax({
		type: "POST",
		url: 'saveuserphoto',
		data: formData,
		contentType: false,
		processData: false,
		dataType: "json",
		complete: function(data) {
			user = JSON.parse(data.responseText);
			if (user !== '') {
				$('#user_photo img').attr("src", 'data:image/jpeg;base64, ' + user.encodedImage);
			}
		}
	});
}

function isEmpty(value) {
	return (value == null || (typeof value === "string" && value.trim().length === 0));
}