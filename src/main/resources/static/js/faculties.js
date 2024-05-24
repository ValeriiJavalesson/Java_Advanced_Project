$(document).ready(function() {
	getAllApplications();
});

function getAllApplications() {
	var allApplications = {};
	$.get("numberOfApplication", function(data) {
		if (data !== '') {
			allApplications = data;
		}
	}).done(function() {
		if (allApplications !== '') {
			$.each(allApplications, function(k, v) {
				$('#numberOfApplication[name="' + k + '"]').html(v);
			});
		}
	});
}

function addFaculty(element) {
	var subjects = [];
	var elemSubject = $(element).parent().parent();
	var subjNames = $(elemSubject).find('li p').toArray();
	var content = '';
	content +=
		"<form class='newFacultyForm col-6 col-lg-4'>  <div class='mb-3'>" +
		"<label for='facultyName' class='form-label'>" + $('#faculties_create_name').val() + "</label>" +
		"<input name='facultyName' type='text' class='form-control' id='facultyName'></div >" +
		"<div class='mb-5 addedSubjectsList d-flex flex-wrap mb-2'> </div>" +
		"<div class='mb-5 subjectsList d-flex flex-row'></div>" +
		"<button type='button' class='btn btn-primary m-1' id='saveFacultyButton' onclick='createFaculty()'>" + $('#faculties_create_save').val() + "</button>" +
		"<button type='button' class='btn btn-danger m-1' onClick='window.location.reload();'>" + $('#faculties_create_cancel').val() + "</button>" +
		"</form >";
	$(".page-content").html(content);
	if (!isEmpty(element)) {
		$('.page-content input#facultyName').val(element.name);

		subjNames.forEach((element) => {
			var content = '';
			content +=
				"<div class='bg-success p-2 text-white bg-opacity-75 m-1 rounded-4' addedSubjects value='" + element.innerText + "'>" + element.innerText +
				"<input name='" + element.innerText + "' type='text' class='form-control' id='" + element.id + "' hidden>" +
				"<button type='button' class='btn-close' aria-label='Close' onclick='removeSubjectFromFaculty(this)'></button>" +
				"</div>";
			$(".addedSubjectsList").append(content);
		});
	}
	var allSubjects = null;
	$.get("allsubjects", function(data) {
		if (data !== '') {
			allSubjects = data;
		}
	}).done(function() {
		var content = '';
		content += "<select class='form-select' id='subject'>";
		allSubjects.forEach((element) => {
			content += "<option id='" + element.id + "'>" + element.name + "</option>";
		});
		content +=
			"</select>" +
			"<button type='button' class='btn btn-primary ms-2' id='addSubjectsButton' onclick='addSubjectsToFaculty()'>+</button>";
		$(".subjectsList").html(content);
	});
}

function addSubjectsToFaculty() {
	var isSubjectPresent = false;
	var element = $("select#subject option:selected").val();
	var elementId = $("select#subject option:selected").attr('id')
	$("div[addedSubjects]").each(function(i, elem) {
		if (element == elem.innerText) {
			isSubjectPresent = true;
		}
	});
	if (!isSubjectPresent) {
		var content = '';
		content +=
			"<div class='bg-success p-2 text-white bg-opacity-75 m-1 rounded-4' addedSubjects value='" + element + "'>" + element +
			"<input name='" + element + "' type='text' class='form-control' id='" + elementId + "' hidden>" +
			"<button type='button' class='btn-close' aria-label='Close' onclick='removeSubjectFromFaculty(this)'></button>" +
			"</div>";
		$(".addedSubjectsList").append(content);
	}
}

function removeSubjectFromFaculty(element) {
	$(element).parent().remove();
}

function createFaculty() {
	var facultyName = $(".newFacultyForm input[name=facultyName]").val();
	if (facultyName === '') return;
	var arr = $("div[addedSubjects] input").toArray();
	var subjects = {};
	arr.forEach((element) => {
		subjects[element.id] = element.name;
	});
	var subjectsAndName = {
		name: facultyName,
		subjects: subjects
	};
	$.ajax({
		type: "POST",
		url: 'savefaculty',
		data: JSON.stringify(subjectsAndName),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		complete: function(data) {
			if (data.responseText === 'success') {
				location.reload();
			} else alert($('#faculties_edit_check').val());
		}
	});
}

function deleteFaculty() {
	var facultyName = $("input#facultyNametoDelete").val();
	var facultyid = $("input#facultyNametoDelete").attr("facultyid");
	if (facultyName === '') return;
	var faculty = { name: facultyName };
	$.ajax({
		url: 'deletefaculty',
		type: 'DELETE',
		data: faculty,
		complete: function(result) {
			if (result.responseText === 'success') {
				$("input#facultyNametoDelete").val("");
				$('.card[id = ' + facultyid + ']').remove();
			} else if (result.responseText === 'notfound') {
				alert($('#faculties_delete_notfound').val());
			} else alert($('#faculties_edit_check').val());
		}
	});

}

function removeFaculty(element) {
	var facultyName = $(element).attr('name');
	$("input#facultyNametoDelete").val(facultyName);
	$("input#facultyNametoDelete").attr("facultyid", $(element).attr("id"));
}

function isEmpty(value) {
	return (value == null || (typeof value === "string" && value.trim().length === 0));
}