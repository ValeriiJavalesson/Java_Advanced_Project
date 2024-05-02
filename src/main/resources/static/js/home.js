

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



