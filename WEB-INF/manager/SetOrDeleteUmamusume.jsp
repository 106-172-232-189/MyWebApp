<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="java.util.List,
			java.util.ArrayList,
			com.umamusumelist.bean.UmamusumeBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width">
		<title>Regist or Delete(Umadex)</title>
		<link rel="stylesheet" href="../../css/Style1.css">
		<link rel="icon" href="../../favicon.ico">
		<link rel="icon" sizes="192x192" href="../../android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="../../apple-touch-icon-180x180.png">
	</head>
	<body>
		<button type="button" onclick="location.href='./'">Top Page of Administrator Pages</button>
		<% List<UmamusumeBean> umamusumeList = (List) request.getAttribute("umamusumeList"); %>
		<h1>Regist or Delete(Umadex)</h1>
		<% String message = (String) request.getAttribute("message"); %>
		<p><%= message == null ? "" : message %></p>
		<h2>Regist</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			Name: <input type="text" name="name" placeholder="1-30 alphabetic characters" title="1-30 alphabetic characters" pattern="^[A-Za-z0-9.]{0,30}$|^[(]Unknown[A-Z]{1,3}[)]$"><br>
			Umadex No.: <input type="text" name="umadexNo" placeholder="1-3 digits" title="1-3 digits" pattern="^(?![8-9８-９][0-9０-９]{2})([1-7１-７][0-9０-９]{2}|[1-9１-９][0-9０-９]|[1-9１-９])$|^(800|８００)$"><br>
			Parameter: <input type="text" name="parameter" placeholder="1-30 alphabetic characters" title="1-30 alphabetic characters" pattern="^[a-z0-9.]{1,30}$"><br>
			<button type="submit" name="button" value="add">Regist</button>
		</form>
		<br>
		<h2>Update</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			Target:
			<select name="target">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeList) { %>
				<option value="<%= u.umadexNo() %>">Umamusume No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select><br>
			New Name: <input type="text" name="name" placeholder="1-30 alphabetic characters" title="1-30 alphabetic characters" pattern="^[A-Za-z0-9.]{0,30}$|^[(]Unknown[A-Z]{1,3}[)]$"><br>
			New Parameter: <input type="text" name="parameter" placeholder="1-30 alphabetic characters" title="1-30 alphabetic characters" pattern="^[a-z0-9.]{1,30}$"><br>
			<button type="submit" name="button" value="update">Update</button>
		</form>
		<br>
		<h2>Delete</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			Target:
			<select name="target">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeList) { %>
				<option value="<%= u.umadexNo() %>">Umamusume No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select><br>
			<button type="submit" name="button" value="delete">Delete</button>
		</form>
		<br>
		<button type="button" onclick="location.href='./'">Top Page of Administrator Pages</button>
	</body>
</html>