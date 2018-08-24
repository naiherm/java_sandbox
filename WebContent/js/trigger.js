$(document).ready(function(){

	$('.trigger').click(function(){
		var n = $(this).next();
		if( n.is(':visible') ){
			n.hide();
		} else{
			n.show();
		}
	});
	
});