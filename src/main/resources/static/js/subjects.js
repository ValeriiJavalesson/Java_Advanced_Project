function addNewSubject() {
	var content = '';
	content +=
		"<form class='w-50'>  <div class='mb-3'>" +
		"<label for='facultyName' class='form-label'>Subject Name</label>" +
		"<input name='subjectName' type='text' class='form-control' id='facultyName'></div >" +
		"<div class='mb-3 d-flex justify-content-between mb-3'>" +
		"<button type='button' class='btn btn-primary' id='saveSubjectButton' onClick='saveNewSubject()'>Save Subject</button>" +
		"<button type='button' class='btn btn-danger' onClick='window.location.reload();'>Cancel</button>" +
		"</div></form >";
	$(".page-content").html(content);
}

function saveNewSubject() {
	newSubjectName = $('input[name=subjectName]').val();
	if (newSubjectName === '') return;
	$.post("savesubject?subjectName=" + newSubjectName, function(data) {
		if (data === 'success') {
			location.reload();
			$('input[name=subjectName]').val('');
		} else if (data === 'update') {
			alert('Subject already exists!');
		} else
			alert('Error!');
	});
}

function deleteSubject() {
	var subjectName = $("input#subjectNametoDelete").val();
	var subjectid = $("input#subjectNametoDelete").attr("subjectid");
	if (subjectName === '') return;
	var subject = { name: subjectName };

	$.ajax({
		url: 'deletesubject',
		type: 'DELETE',
		data: subject,
		complete: function(result) {
			if (result.responseText === 'success') {
				$("input#subjectNametoDelete").val("");
				$('.card[id = ' + subjectid + ']').remove();
			} else if (result.responseText === 'notfound') {
				alert("Subject not found");
			} else alert("Check input!");
		}
	});
}
function removeSubject(element) {
	var subjectName = $(element).attr('name');
	$("input#subjectNametoDelete").val(subjectName);
	$("input#subjectNametoDelete").attr("subjectid", $(element).attr("id"));
}