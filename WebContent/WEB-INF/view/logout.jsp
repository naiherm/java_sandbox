<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
</head>
<body>


<!-- メッセージ部 -->
<%
	String msg = (String)request.getAttribute("msg");
%>
<% if( msg != null ){ %>
<h3 class="trigger">Info</h3>
<div><%= msg %></div>
<% } %>



<a href="Index">トップに戻る</a>

</body>
</html>