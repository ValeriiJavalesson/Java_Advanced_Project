const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))

function addReport(element) {
	$("table tbody").html('');
	if (!isEmpty(element)) {
		var faculty = $(element).attr('name');
		var facultyid = $(element).attr('facultyid');
		var content = "<select class='facultieslist form-select fw-bolder text-center' aria-label='select' onchange='getApplications(this)'>";
		content += "<option id='" + facultyid + "' value='" + faculty + "'>" + faculty + "</option></select>";
		$(".page-content").html(content);
		$(".table-content").removeClass('d-none');
		getApplications(element);
	} else {
		var content = "<select class='facultieslist form-select fw-bolder text-center' aria-label='select' onchange='getApplications(this)'>";
		var facultiesStr = $('#addNewReport').attr('allfaculties');
		var faculties = JSON.parse(facultiesStr);
		if (!isEmpty(faculties)) {
			$.each(faculties, function(k, v) {
				content +=
					"<option id='" + k + "' value='" + v + "'>" + v + "</option>"
			});
		}
		content += "</select>";
		$(".page-content").html(content);
		$(".table-content").removeClass('d-none');
		getApplications($(".facultieslist"));
	}
}

function getApplications(element) {
	$("table tbody").html('');
	var faculty = '';
	if (!isEmpty(element)) {
		faculty = $(element).val();
	}
	var allApplications = {};
	$.get("applicationsByFaculty", { facultyName: faculty }, function(data) {
		if (data.length > 0) {
			allApplications = data;
		}
	}).done(function() {
		if (allApplications.length > 0) {
			allApplications.forEach((element) => {
				var points = element.certificatePoints;
				$.each(element.subjects, function(k, v) {
					points += v;
				});
				var content = '';
				content +=
					"<tr><td><div class='align-items-center d-flex form-check justify-content-center'>" +
					"<input class='form-check-input' type='checkbox' value='" + element.id + "' id='flexCheckDefault' onclick='viewButtons();'><label class='form-check-label' for='flexCheckDefault'></label></div></td>" +
					"<td>" + element.firstname + "</td>" +
					"<td>" + element.lastname + "</td>" +
					"<td class ='text-center'>" + points + "</td></tr>";
				$("table tbody").append(content);

			});
			var report = '';
			$.get("getreport", { facultyName: faculty }, function(data) {
				if (data !== '') {
					report = data;
				}
			}).done(function() {
				if (report !== '') {
					$('.buttons-group #numberOfStudents').val(report.numberOfStudents);
					var addedAppl = report.applications;
					var allInputs = Array.from($('table input'));
					for (var i = 0; i < allInputs.length; i++) {
						if (addedAppl.some((appl) => appl.id == $(allInputs[i]).val())) {
							$(allInputs[i]).attr('checked', 'checked');
						}
						viewButtons();
					}
				}
			});
		} else { $(".saveButton").addClass('disabled'); }

	});
	$('.buttons-group').removeClass('d-none');
}

function viewButtons() {
	var allInputs = Array.from($('table input'));
	if (allInputs.some((el) => $(el).is(":checked")) && ($('.buttons-group #numberOfStudents').val()) > 0) {
		$('.saveButton').removeClass('disabled');
	} else
		$(".saveButton").addClass('disabled');
}

function selectAllApplications(elem) {
	let listOfElement = $(".form-check-input");
	let arrayOfElement = [];
	for (var i = $(".form-check-input").length >>> 0; i--;) {
		arrayOfElement[i] = listOfElement[i];
	}
	if (arrayOfElement.some((x) => x.checked === false)) {
		arrayOfElement.forEach(function(element) {
			$(element).prop("checked", true);
		});
		$(elem).attr("data-bs-original-title", "Deselect all");
		$(".tooltip-inner").text("Deselect all");
	} else {
		arrayOfElement.forEach(function(element) {
			$(element).prop("checked", false);

		});
		$(".tooltip-inner").text("Select all");
		$(elem).attr("data-bs-original-title", "Select all");
	}
	viewButtons();
}

function saveReport() {
	var faculty = $(".facultieslist").val();
	var allInputs = Array.from($('table input'));
	var numberOfStudents = $('.buttons-group #numberOfStudents').val();
	var applicationIdList = [];
	allInputs.forEach((element) => {
		if ($(element).is(":checked")) {
			applicationIdList.push($(element).val());
		}
	});
	var form = {
		faculty: faculty,
		applicationIdList: applicationIdList,
		numberOfStudents: numberOfStudents
	}
	$.ajax({
		type: "POST",
		url: "savereport",
		traditional: true,
		data: form,
		success: function(data) { if (data === 'success') location.reload(); }
	});
}
function deleteReport() {
	var reportid = $("input#reportNametoDelete").attr("reportid");
	if (reportid === '') return;
	var report = { id: reportid };
	$.ajax({
		type: "DELETE",
		url: "deletereport",
		data: report,
		success: function(data) {
			if (data === 'success') {
				location.reload();
			} else if (data === 'notfound') {
				alert("Report not found");
			} else alert("Check input!");
		}
	});
}

function removeReport(element) {
	var facultyName = $(element).attr('name');
	$("input#reportNametoDelete").val(facultyName);
	$("input#reportNametoDelete").attr("reportid", $(element).attr("id"));
}

function showResult(element) {
	var facultyId = $(element).children(":first").attr('id');
	var facultyName = $(element).children(":first").text();
	var resultList = '';
	$.get('report?id=' + facultyId, function(data) {
		if (!isEmpty(data)) {
			resultList = data;
		}
	}).done(function() {
		if (!isEmpty(resultList)) {
			var content = "<div class='fw-bold h3'>Faculty: " + facultyName + "</div>" +
				"<div class='fw-bold h4 mb-2'>Winners:</div>";
			$(".page-content").html(content);
			$(".table-content").removeClass('d-none');
			content = "<table class='table bg-light table-group-divider table-bordered'><thead><tr>" +
				"<th scope='col' class='text-center col-4'>Firstname</th>" +
				"<th scope='col' class='text-center col-4'>Lastname</th>" +
				"<th scope='col' class='text-center col-2'>Total points</th>" +
				"</tr></thead><tbody></tbody></table>";
			$(".table-content").html(content);
			resultList.forEach((element) => {
				var points = element.certificatePoints;
				$.each(element.subjects, function(k, v) {
					points += v;
				});
				content = '';
				content +=
					"<tr><td>" + element.firstname + "</td>" +
					"<td>" + element.lastname + "</td>" +
					"<td class ='text-center'>" + points + "</td></tr>";
				$("table tbody").append(content);

			});
			$(".table-content").append("<a type='button' class='btn btn-warning ms-2' href='reports'>Back</a>");
		}
	});
}

function isEmpty(value) {
	return (value == null || (typeof value === "string" && value.trim().length === 0));
}