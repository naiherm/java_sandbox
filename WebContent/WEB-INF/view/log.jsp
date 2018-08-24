<%@page import="model.Account"%>
<%@page import="model.ReportDAO"%>
<%@page import="model.Report"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>過去ログ参照</title>
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="js/trigger.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css" /></head>
<body>
<%
	Account user = (Account)session.getAttribute("user");
	List<Report> reps = (List<Report>)request.getAttribute("reps");
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

<form action="Main" method="post">
<input type="submit" value="メインに戻る" />
</form>
</div>

<!-- メッセージ部 -->
<%
	String msg = (String)request.getAttribute("msg");
%>
<% if( msg != null ){ %>
<h3 class="trigger">Info</h3>
<div><%= msg %></div>
<% } %>




<h3 class="trigger">過去ログ一覧</h3>
<div>
<% for(int i=0; i< reps.size(); i++ ){
	Report r = reps.get(i);
	%>
<h4 class="trigger"><%= r.getDay() %></h4>
<dl>
<dt>作業内容</dt>
<dd><%= r.getCategoryName() %></dd>
<dd><textarea cols="50" rows="5" disabled="disabled"><%= r.getDoing() %></textarea></dd>
<dt>当日進捗％</dt>
<dd><%= r.getTodayProgress() %></dd>
<dt>全体進捗％</dt>
<dd><%= r.getFinalProgress() %></dd>
<dt>完成内容</dt>
<dd><textarea cols="50" rows="5" disabled="disabled"><%= r.getComplete() %></textarea></dd>
<dt>残作業</dt>
<dd><textarea cols="50" rows="5" disabled="disabled"><%= r.getRemains() %></textarea></dd>
<dt>雑感</dt>
<dd><textarea cols="50" rows="10" disabled="disabled"><%= r.getComment() %></textarea></dd>
</dl>
<% } %>
</div>



</body>
</html>