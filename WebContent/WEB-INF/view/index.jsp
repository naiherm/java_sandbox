<%@page import="model.Team"%>
<%@page import="java.util.List"%>
<%@page import="model.Account"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DailyReport</title>
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="js/trigger.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css" />
</head>
<body>
<%
	Account user = (Account)session.getAttribute("user");
	String lid = user != null ? user.getLid() : "";
	String name = user != null ? user.getName() : ""; 
	String pass = user != null ? user.getPass() : ""; 
	
	List<Team> team = (List<Team>)request.getAttribute("team");
%>

<!-- メッセージ部 -->
<%
	String msg = (String)request.getAttribute("msg");
%>
<% if( msg != null ){ %>
<h3 class="trigger">Info</h3>
<div><%= msg %></div>
<% } %>


<!-- ログイン -->
<h3 class="trigger">ログイン</h3>
<div>
<form action="Index" method="post">
<table>
<tr><th>ログインID</th><td><input type="text" name="lid" value="<%= lid %>" /></td></tr>
<tr><th>パスワード</th><td><input type="password" name="pass" value="<%= pass %>" /></td></tr>
</table>
<input type="hidden" name="act" value="login" />
<input type="submit" value="Login" />
</form>
</div>

<hr>

<!-- 新規登録  -->
<h3 class="trigger">新規登録</h3>
<div>
<form action="Index" method="post">
<table>
<tr><th>ログインID</th><td><input type="text" name="lid" value="<%= lid %>" /></td></tr>
<tr><th>パスワード</th><td><input type="password" name="pass" value="<%= pass %>" /></td></tr>
<tr><th>氏名</th><td><input type="text" name="name" value="<%= name %>" /></td></tr>
<tr><th>グループ</th>
<td>
<select name="tid">
<% for( int i = 0; i < team.size(); i++ ){
	Team t = team.get(i);
%>
<option value="<%= t.getId()%>"><%= t.getCaption() %></option>
<% }%>
</select>
</td></tr>
</table>
<input type="hidden" name="act" value="create" />
<input type="submit" value="Create" />
</form>
</div>

<hr>



</body>
</html>
