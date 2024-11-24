<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="java.util.List,
			java.util.ArrayList,
			bean.UmamusumeBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width">
		<title>登録/削除(図鑑番号)</title>
		<link rel="stylesheet" href="../../css/Style1.css">
		<link rel="icon" href="../../favicon.ico">
		<link rel="icon" sizes="192x192" href="../../android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="../../apple-touch-icon-180x180.png">
	</head>
	<body>
		<button type="button" onclick="location.href='./'">管理者専用トップページに戻る</button>
		<% List<UmamusumeBean> umamusumeList = (List) request.getAttribute("umamusumeList"); %>
		<h1>登録/削除(図鑑番号)</h1>
		<% String message = (String) request.getAttribute("message"); %>
		<p><%= message == null ? "" : message %></p>
		<h2>登録</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			名前: <input type="text" name="name" placeholder="全角カタカナ30文字以内" pattern="^[\u30A0-\u30FF]{0,30}$|^[(]不明[0-9]{1,3}[)]$"><br>
			パラメーター: <input type="text" name="parameter" placeholder="半角英字30文字以内" pattern="^[a-z0-9.]{0,30}$"><br>
			<button type="submit" name="button" value="add">登録</button>
		</form>
		<br>
		<h2>名前変更</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			変更対象:
			<select name="target">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeList) { %>
				<option value="<%= u.umadexNo() %>">No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select><br>
			変更後の名前: <input type="text" name="name" placeholder="全角カタカナ1文字～30文字" pattern="^[\u30A0-\u30FF]{0,30}$|^[(]不明[0-9]{1,3}[)]$"><br>
			変更後のパラメーター: <input type="text" name="parameter" placeholder="半角英字1文字～30文字" pattern="^[a-z0-9.]{1,30}$"><br>
			<button type="submit" name="button" value="update">変更</button>
		</form>
		<br>
		<h2>削除</h2>
		<form action="SetOrDeleteUmamusume" method="post">
			削除対象:
			<select name="target">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeList) { %>
				<option value="<%= u.umadexNo() %>">No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select><br>
			<button type="submit" name="button" value="delete">削除</button>
		</form>
		<br>
		<button type="button" onclick="location.href='./'">管理者専用トップページに戻る</button>
	</body>
</html>