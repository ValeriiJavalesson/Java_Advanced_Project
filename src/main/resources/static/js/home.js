var appl = null;
$.get("appl", function(data) {
	if (data !== '') {
		buckets = data;
	}
}).done(function() {


	var tableContent = "<tr class='header'>" +
		"<th style='width: 20%;' class='text-center'>Назва</th>" +
		"<th style='width: 20%;' class='text-center'>Опис</th>" +
		"<th style='width: 20%;' class='text-center'>Ціна</th>" +
		/*	"<th style='width: 20%;' class='text-center'>Дата покупки</th>" +*/
		"<th style='width: 20%;' class='text-center'>Options</th>" +
		"</tr>";
	var sum = 0;
	jQuery.each(buckets, function(i, value) {
		sum += value.price;
		tableContent += "<tr class='table-row'>" +
			"<td class='text-center'>" + value.title + "</td>" +
			"<td class='text-center'>" + value.description + "</td>" +
			"<td class='text-center'>" + value.price + " $</td>" +
			/*	"<td class='text-center'>" + value.purchaseDate + "</td>" +*/
			"<td class='text-center'><button class='btn-close' onclick='deleteOrderFromBucket(" + value.bucketId + ")'></button></td>" +
			"</tr>"

	});
	sum = sum.toFixed(2);
	tableContent += "<tr class='total-row mt-1'>" +
		"<td colspan='2' class='text-center'> Загальна сума: </td>" +
		"<td class='text-center'>" + sum + " $</td>" +
		"<td class='text-center'><button onclick='' class='btn btn-primary'>Купити</button></td>" +
		"</tr>"

	$('#myTable').html(tableContent);

	table = document.getElementById("myTable");
	tr = table.getElementsByTagName("tr");
	if (tr.length == '2') {
		$('.main-content').css('display', 'none');
		$('.empty-cart-container').css('display', 'flex');
	}
	else {
		$('.main-content').css('display', 'block');
		$('.empty-cart-container').css('display', 'none');
	}

});