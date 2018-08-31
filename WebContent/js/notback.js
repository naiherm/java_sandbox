/**
 * ブラウザボタンによる遷移阻止用
 * @author med
 */
$(document).ready(function(){

	// ドキュメント読み込み時に手動でヒストリーに戻り先のないヒストリーを追加
	// これがないと、ブラウザのボタン押下 popState イベントが送信されない模様
	history.pushState( null, null, null );

	// ブラウザ Back Prev 操作時の処理
	$(window).on('popstate', function(e){
		// 移動イベントが発生した際にも、ヒストリーに戻り先のないヒストリーを追加
		history.pushState( null, null, null );
	 });
	
	
	// キーボード押下抑制
	$(window).on('load', function(){
		// event hook
		this.addEventListener("keydown",disablekey,false);
	//	this.addEventListener('beforeunload', func, false);
	});
//	// アンロード無視
//	function func(event){
//		event.stopPropagation();
//		event.preventDefault();
//	}
	
	// F5 キーと Backspaceキーを無効化
	function disablekey(event){
		var code = event.keyCode;
		// f5 = 116 
		// backspace = 8
		if(code === 116 ||code === 8){
			event.preventDefault();
			event.returnValue=false;
		}
	}	
});