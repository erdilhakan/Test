$('document').ready(function(){

	 $('.ui-selectmenu-filter').bind('click keyup keydown',function(event){
		
		 $(this).val(($(this).val()).toUpperCase());
		 
	 });
	
	  $('input').bind('click keyup keydown hover', function(event) {
		
		 $(this).css("border-color","yellow");
		 
		 
		 
	  });
	  $('input').bind('blur', function(event) {
		 
		  $(this).css("border-color","#999fd9");
		  $(this).css("font-weight","normal");
		  
		  });
	  
	  $('textarea').bind('click keyup keydown hover', function(event) {
			 
			
			 $(this).css("border-color","yellow");
			 
			 
			 
		  });
		  $('textarea').bind('blur', function(event) {
			 
			  $(this).css("border-color","#999fd9");
			  $(this).css("font-weight","normal");
			  
			  });
		 $('.ui-selectonemenu').bind('click keyup keydown hover', function(event) {
			
			 $(this).css("border-color","yellow");
			
		 $(this).val().toUpperCase();
	  });
	  $('.ui-selectonemenu').bind('blur', function(event) {
		  $(this).css("border-color","#999fd9");
		  $(this).css("font-weight","normal"); 
		  });

	  $('button').bind('mouseover keyup click', function(event) {
			 
			 $(this).css("font-weight","bold");
			 $(this).css("background-color","#ddca86");
		  });
	  $('button').bind('mouseout blur', function(event) {
			 
			 $(this).css("font-weight","normal");
			 $(this).css("background-color","#6986c9");
		  });
	  
	  /*
	  $('#link').click(function(e) {
		 
		  var offset = $(this).offset();
		  $('#form2').offset({ top: offset.top, left: offset.left+10  });
		  });	 
		*/
	  
	
	  $(function() {
		$('#duration').timepicker({ 'minTime': '07:00', 'maxTime': '21:00','step':'5','timeFormat': 'H:i:s'});
	  });

	/*
	 $(".ui-panelmenu-content").css("display","block");
	    $(".ui-panelmenu-header").addClass("ui-state-active");
	    $(".ui-icon-triangle-1-e").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
	*/
	   
	    $(function(){
	    	
	    		    	
	    	var matches = $('.ui-panelmenu-panel a').filter(function () {
	    		return document.location.href.indexOf("basvuru")>=0;
	           //return document.location.href.indexOf($('input[id="hidden"]').val())>=0;
	        });
	    	//matches.css('background-color','red');
	    	$('.ui-panelmenu-panel a[id="basvuru"]').addClass('ui-state-active');
	    	/*
	       matches.header.css("display","block");
	        matches.css('display','none');
	      // matches.addClass('ui-state-active');
	        $(".ui-icon-triangle-1-e").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s");
	        */
	    });
	  

});




/*
$(function () {
	alert($('input[id="hidden"]').val());  	
    var menus = $('#ui-menuitem-link');
    alert(menus.attr('href'));
	var matches = menus.filter(function () {
        return document.location.href.indexOf($(this).id($('input[id="hidden"]').val())) >= 0;
    });
    
    matches.parent.show();
});
*/



