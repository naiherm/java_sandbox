/**
 * クラス属性 trigger の要素をクリックすると
 * 直後の要素の表示非表示が切り替わる
 * @author med
 */
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