<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
	import="java.util.List,
			java.util.ArrayList,
			com.umamusumelist.bean.UmamusumeBean,
			com.umamusumelist.bean.RacingUmamusumeBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width">
		<title>登録/削除(勝負服)</title>
		<link rel="stylesheet" href="../../css/Style1.css">
		<link rel="icon" href="../../favicon.ico">
		<link rel="icon" sizes="192x192" href="../../android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="../../apple-touch-icon-180x180.png">
		<script src="../../js/DisableController.js"></script>
		<script src="../../js/TodayGetter.js"></script>
	</head>
	<body>
		<button type="button" onclick="location.href='./'">管理者専用トップページに戻る</button>
		<% List<UmamusumeBean> umamusumeListNotExclusive = (List) request.getAttribute("umamusumeListNotExclusive"); %>
		<% List<UmamusumeBean> umamusumeListExclusive = (List) request.getAttribute("umamusumeListExclusive"); %>
		<% List<RacingUmamusumeBean> racingUmamusumeListNotExclusive = (List) request.getAttribute("racingUmamusumeListNotExclusive"); %>
		<% List<RacingUmamusumeBean> racingUmamusumeListExclusive = (List) request.getAttribute("racingUmamusumeListExclusive"); %>
		<h1>登録/削除(勝負服)</h1>
		<% String message = (String) request.getAttribute("message"); %>
		<p><%= message == null ? "" : message %></p>
		<form action="SetOrDeleteRacingUmamusume" method="post">
			<p>
				<input type="radio" name="type" value="false" onclick="ex(this.checked); nex(!this.checked);">通常&nbsp;
				<input type="radio" name="type" value="true" onclick="nex(this.checked); ex(!this.checked);">特殊
			</p>
			<h2>追加</h2>
			名前:
			<select name="target" class="notExclusive" disabled="disabled">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeListNotExclusive) { %>
				<option value="<%= u.umadexNo() %>">No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select>
			<select name="target2" class="exclusive" disabled="disabled">
				<option value="0"></option>
				<% for (UmamusumeBean u : umamusumeListExclusive) { %>
				<option value="<%= u.umadexNo() %>">No.<%= u.umadexNo() %>: <%= u.name() == null ? "" : u.name() %></option>
				<% } %>
			</select><br>
			勝負服登録日(通常): <input type="date" name="appeared" class="notExclusive" disabled="disabled" id="today"><br>
			勝負服番号(特殊): <input type="text" class="exclusive" name="no" placeholder="2桁までの番号" title="2桁までの番号" pattern="^[0-9０-９]{1,2}$" disabled="disabled"><br>
			<button type="submit" name="button" value="add">追加</button>
			<h2>削除</h2>
			名前:
			<select name="target3" class="notExclusive" disabled="disabled">
				<option value="0"></option>
				<% for (RacingUmamusumeBean ru : racingUmamusumeListNotExclusive) { %>
				<option value="<%= ru.racingSuitNo() %>">勝負服<%= ru.racingSuitNo() %>号: <%= ru.name() == null ? "" : ru.name() %></option>
				<% } %>
			</select>
			<select name="target4" class="exclusive" disabled="disabled">
				<option value="0"></option>
				<% for (RacingUmamusumeBean ru : racingUmamusumeListExclusive) { %>
				<option value="<%= ru.racingSuitNo() %>">EX勝負服<%= ru.racingSuitNo() %>号: <%= ru.name() == null ? "" : ru.name() %></option>
				<% } %>
			</select><br>
			<button type="submit" name="button" value="delete">削除</button>
		</form>
	</body>
</html>