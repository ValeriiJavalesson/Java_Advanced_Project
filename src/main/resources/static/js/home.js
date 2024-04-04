function getUserApplication() {
	var appl = null;
	$.get("application", function(data) {
		appl = data;
	}).done(function() {
		var content = '';
		if (appl === '') {
			content +=
				"<div class='mb-3'>You don't have application.</div><button type='button' class='btn btn-primary m-1' id='createNewApplicationButton' onclick = 'editApplication()'>Create new applicant application</button>";
		} else {
			var totalPoints = appl.certificatePoints;
			content +=
				"<table class='table text-center'><thead><tr><th scope='col'>Faculty</th><th scope='col' class='visually-hidden'>User name</th><th scope='col'>Certificate points</th>";
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
				"<td>" + totalPoints + "</td><td><button type='button' class='btn btn-outline-secondary' id='editApplicationButton' onclick = 'editApplication()'>Edit</button></td>" +
				"</tr></tbody></table>";
		}
		$(".applicationform").html(content);
	});
};

function editApplication() {
	var appl = null;
	$.get("application", function(data) {
		if (data !== '') {
			appl = data;
		}
	}).done(function() {
		var faculty = '';
		var subjects = '';
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

function getListFacultites() {
	$(".applicationform").html("<select class='form-select text-center w-25' id='facultiesList' onChange='viewAllStudents()'></select>" + "<div class='mb-3' id='userList'></div>");
	var faculties = null;
	$.get("faculties", function(data) {
		if (data !== '') {
			faculties = data;
		}
	}).done(function() {
		var content = '';
		faculties.forEach((element, i) => {
			if (i === 0)
				content += "<option selected>" + element.name + "</option>";
			else
				content += "<option>" + element.name + "</option>";
		});
		$("#facultiesList").html(content);
		viewAllStudents();
	});
}

function getFacultites(faculty, subjects) {
	var faculties = null;
	$.get("faculties", function(data) {
		if (data !== '') {
			faculties = data;
		}
	}).done(function() {
		var content = '';
		faculties.forEach((element, i) => {
			if (element.name === faculty)
				content += "<option selected>" + element.name + "</option>";
			else
				content += "<option>" + element.name + "</option>";
		});
		$("#facultiesList").html(content);
		getFacultySubjects(subjects);
	});
}

function getFacultySubjects(user_subjects) {
	var facultyName = $("select#facultiesList option:selected").val();
	if (facultyName === '') facultyName = $("select#facultiesList option:first").val();
	var subjects = null;
	$.get("facultysubjects?facultyName=" + facultyName, function(data) {
		if (data !== '') {
			subjects = data;
		}
	}).done(function() {
		var content = '';
		subjects.forEach((element) => {
			var points = null;
			if (!isEmpty(user_subjects))
				points = user_subjects[element.name];
			content +=
				"<label for='subject' class='form-label'>" + element.name + "</label>" +
				"<input type='number' class='form-control' id='subject' name='" + element.id + "' value='" + points + "'>";
		});
		$("#applicationSubjectsList").html(content);
	});
}


function createApplication() {
	var certificatePoints = $("form input#certificatePoints").val();
	var faculty = $("select#facultiesList option:selected").val();
	var subjectsNamesArray = $("div#applicationSubjectsList label").toArray();
	var subjects = {};
	subjectsNamesArray.forEach((element, i) => {
		subjects[element.innerHTML] = element.nextElementSibling.value;
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
				alert("Successfully!");
				getUserApplication();
			} else alert("Check inputs!");
		}
	});
}

function addFaculty() {
	var content = '';
	content +=
		"<form class='newFacultyForm col-6 col-lg-4'>  <div class='mb-3'>" +
		"<label for='facultyName' class='form-label'>Faculty Name</label>" +
		"<input name='facultyName' type='text' class='form-control' id='facultyName'></div >" +
		"<div class='mb-5 addedSubjectsList d-flex flex-wrap mb-2'> </div>" +
		"<div class='mb-5 subjectsList d-flex flex-row'></div>" +
		"<button type='button' class='btn btn-primary m-1' id='saveFacultyButton' onclick='createFaculty()'>Save Faculty</button>" +
		"<button type='button' class='btn btn-danger m-1' id='deleteFacultyButton' onclick='deleteFaculty()'>Delete Faculty</button>" +
		"</form >";
	$(".applicationform").html(content);
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
		facultyName: facultyName,
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
				alert("Successfully created!");
				$(".addedSubjectsList").children().remove();
				$(".newFacultyForm input[name=facultyName]").val("");
			} else alert("Check inputs!");
		}
	});
}

function deleteFaculty() {
	var facultyName = $(".newFacultyForm input[name=facultyName]").val();
	if (facultyName === '') return;
	$.post("deletefaculty", facultyName, function(data) {
		if (data === 'success') {
			alert("Successfully deleted!");
			$(".addedSubjectsList").children().remove();
			$(".newFacultyForm input[name=facultyName]").val("");
		} else if (data === 'notfound') {
			alert("Faculty not found");
		} else alert("Check input!");
	});
}

function addNewSubject() {
	var content = '';
	content +=
		"<form>  <div class='mb-3'>" +
		"<label for='facultyName' class='form-label'>Subject Name</label>" +
		"<input name='subjectName' type='text' class='form-control' id='facultyName'></div >" +
		"<div class='mb-3'>" +
		"</div>  <button type='button' class='btn btn-primary' id='saveSubjectButton' onClick='saveNewSubject()'>Save Subject</button>" +
		"</form >";
	$(".applicationform").html(content);
}

function saveNewSubject() {
	newSubjectName = $('input[name=subjectName]').val();
	if (newSubjectName === '') return;
	$.post("savesubject?subjectName=" + newSubjectName, function(data) {
		if (data === 'success') {
			alert('Successfully created!');
			$('input[name=subjectName]').val('');
		} else if(data === 'update'){
			alert('Subject already exists!');
		}else 
			alert('Error!');
	});
}

function viewAllStudents() {
	var facultyName = $("select#facultiesList option:selected").val();
	var allAppl = null;
	var url = "allapplication?facultyName=" + facultyName;
	$.get(url, function(data) {
		allAppl = data;
	}).done(function() {
		content = '';
		if (allAppl === '' || allAppl.length === 0) {
			$("#userList").html("<div class='m-3'>No one application was created.</div>");
		} else {
			$("#userList").html('');
			content +=
				"<table class='table text-center'><thead class='border-black border-bottom'><tr><th scope='col'>User name</th><th scope='col'>Certificate points</th>";
			$.each(allAppl[0].subjects, function(k, v) {
				content +=
					"<th scope='col' >" + k + "</th>";
			});
			content += "<th scope='col'>Total points</th></tr></thead><tbody>"
			allAppl.forEach(function(appl) {
				var totalPoints = 0;
				totalPoints += appl.certificatePoints;
				content +=
					"<tr><td>" + appl.username + "</td><td>" + appl.certificatePoints + "</td>";
				$.each(appl.subjects, function(k, v) {
					totalPoints += v;
					content += "<td>" + v + "</td>";
				});
				content += "<td>" + totalPoints + "</td></tr>";
			});
			content += "</tbody></table>";
			$("#userList").append(content);
		}
	});
}

function isEmpty(value) {
	return (value == null || (typeof value === "string" && value.trim().length === 0));
}

