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
	
});