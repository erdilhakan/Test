$('document').ready(function(){


	$("#binacomplete").bind('keyup', function (e) {
		
	    if (e.which >= 97 && e.which <= 122) {
	        var newKey = e.which - 32;
	        // I have tried setting those
	        e.keyCode = newKey;
	        e.charCode = newKey;
	    }

	    $("#binacomplete").val(($("#binacomplete").val()).toUpperCase());
	});


});



