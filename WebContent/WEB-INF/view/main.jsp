<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="model.Task"%>
<%@page import="model.Report"%>
<%@page import="model.TaskCategory"%>
<%@page import="model.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DailyReport main</title>
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="js/trigger.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css" />
</head>
<body>
<%
	String msg = (String)request.getAttribute("msg");

	Account user = (Account)session.getAttribute("user");
	List<TaskCategory> tCate = (List<TaskCategory>)session.getAttribute("tCate");
	List<Task> tasks = (List<Task>)session.getAttribute("tasks");
	
	
	Report rep = (Report)request.getAttribute("report");
	if( rep == null ){
		rep = new Report();
	}
	// 日数関連の表示コントロール
	Calendar cal = Calendar.getInstance();
%>

<!-- アカウント関連 -->
<h3 class="trigger">Your Account</h3>
<div id="account">
<dl>
<dt>氏名</dt>
<dd><%= user.getName() %></dd>
<dt>グループ名</dt>
<dd><%= user.getTeamCaption() %></dd>
</dl>
<form action="Setting" method="post">
<input type="hidden" name="act" value="logout">
<input type="submit" value="ログアウト" />
</form>

<form action="Setting" method="post">
<input type="hidden" name="act" value="change">
<input type="submit" value="変更" disabled="disabled" />
</form>


<form action="LogShow" method="post">
<input type="hidden" name="act" value="log">
<input type="submit" value="過去ログ参照" />
</form>

</div>


<!-- チーム関連 -->
<h3 class="trigger">Team Setting</h3>
<div id="setting">
<p>チーム用の設定画面</p>
</div>



<!-- 日報関連 -->
<h3 class="trigger">Your Report</h3>
<div id="report">

<% if( msg != null ){ %>
<h3 class="trigger">Info</h3>
<div><%= msg %></div>
<% } %>

<form action="SubmitReport" method="post">
<dl>
<dt>日付</dt>
<dd><%=  cal.get(Calendar.YEAR)%>年<%=  cal.get(Calendar.MONTH) + 1%>月<%=  cal.get(Calendar.DATE)%>日</dd>
<dt>担当予定項目</dt>
<dd>
<table>
	<tr>
	<th>カテゴリ</th>
	<th>項目名</th>
	<th>詳細</th>
	<th>優先度</th>
	<th>進捗</th>
	</tr>
<% for(int i=0; i<tasks.size(); i++){
		Task t = tasks.get(i);
%>
	<tr>
	<td><%= t.getCategory() %></td>
	<td><%= t.getCaption() %></td>
	<td><%= t.getDiscription() %></td>
	<td><%= t.getPriority() %></td>
	<td><%= t.getProgress()%></td>
	</tr>
<% } %>
</table>
</dd>
<dt>本日の作業内容</dt>
<dd>
<select name="catid">
<% 
	for(int i=0; i<tCate.size(); i++ ){
		TaskCategory tc = tCate.get(i);
		String flag = rep.getCategoryID() == tc.getId() 
						? " selected=\"selected\"" 
						: "";
	%>
<option value="<%= tc.getId() %>"<%= flag %>><%= tc.getCaption() %></option>
<% 
	}
%>
</select>
</dd>
<dd><textarea name="doing" cols="50" rows="5"><%= rep.getDoing() %></textarea></dd>
<dt>本日内容の進捗度 % (0~100)</dt>
<dd><span class="num" id="tpv"><%= rep.getTodayProgress() %></span><input id="tp" name="todayProgress" type="range" step="1" min="0" max="100" value="<%= rep.getTodayProgress() %>" /></dd>
<dt>完成までの進捗度 % (0~100)</dt>
<dd><span class="num" id="fpv"><%= rep.getFinalProgress() %></span><input id="fp" name="finalProgress" type="range" step="1" min="0" max="100" value="<%= rep.getFinalProgress() %>" /></dd>
<dt>完成内容</dt>
<dd><textarea name="complete" cols="50" rows="5"><%= rep.getComplete() %></textarea></dd>
<dt>残作業</dt>
<dd><textarea name="remains" cols="50" rows="5"><%= rep.getRemains() %></textarea></dd>
<dt>雑感</dt>
<dd><textarea name="comment" cols="50" rows="10"><%= rep.getComment() %></textarea></dd>
</dl>

<input type="submit" class="button" value="提出" />
</form>
</div>



<script type="text/javascript">
$(document).ready(function(){
	
	// input[type=range] のスライダ動作用 
	$('#tp').change(function(){
		$('#tpv').text( $(this).val() );	
	});
	$('#fp').change(function(){
		$('#fpv').text( $(this).val() );	
	});
			
});
</script>

</body>
</html>
