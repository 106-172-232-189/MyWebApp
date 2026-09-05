<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.List,
			java.util.ArrayList,
			com.umamusumelist.bean.UmamusumeBean,
			com.umamusumelist.bean.NotUmamusumeBean,
			com.umamusumelist.util.KatakanaToHankaku,
			com.umamusumelist.util.NumberSuffix,
			java.time.LocalDateTime,
			java.time.format.DateTimeFormatter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width">
		<meta name='description' content='This is the Character List of Umamusume.'>
		<title>Umamusume Character List</title>
		<link rel="stylesheet" href="./css/Style1.css">
		<link rel="stylesheet" href="./css/Style4.css">
		<link rel="stylesheet" href="./css/Style6.css">
		<link rel="icon" href="./favicon.ico">
		<link rel="icon" sizes="192x192" href="./android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon-180x180.png">
		<script src="./js/ShowConfirm.js"></script>
	</head>
	<body>
		<button type="button" onclick="location.href='./'">Top Page</button><button type="button" onclick="location.href='./RacingUmamusumeList'">Playable Character List</button><br>
		<button type="button" onclick="location.href='../ja/UmamusumeList'">Japanese Page/日本語版ﾍﾟｰｼﾞ</button>
		<h1>Umamusume Character List</h1>
		<div class="div9" style="text-align: center;"><img src="./img/Umamusume_Top_Number_Trio.jpg" alt="Special Week, Silence Suzuka, and Tokai Teio strike a pose" class="imgB" style="display: block; margin: auto;"></div>
		<div class="div4" style="text-align: center;">©Cygames</div>
		<br>
		<div>
			<% String id = (String) request.getAttribute("id"); %>
			<% int noMaxA = (int) request.getAttribute("noMaxA"); %>
			<% int noMaxB = (int) request.getAttribute("noMaxB"); %>
			<form action="UmamusumeList" method="post">
				<input type="text" name="id" value="<%= id == null ? "" : id %>" placeholder="Umadex No.1-800 or Name" title="'Umadex No.1-800 or Name'/'1-3 digits or 1-30 alphabetic characters'" size="30" pattern="(^[0-9０-９]{1,3}$)|(^[A-Za-z.]{0,30}$)">
				<button type="submit">Search</button>
			</form>
			<br>
			<div class="div9" style="text-align: center;">
				<table>
					<tr>
						<th class="th-sp">Umadex<br><div class="div5">(&amp;Trainee<br>Umadex)</div></th><th>Name</th>
					</tr>
					<% List<UmamusumeBean> umamusumeList = (List) request.getAttribute("umamusumeList"); %>
					<% for (UmamusumeBean u : umamusumeList) { %>
						<% if (u.umadexNo() <= 800) { %>
					<tr>
						<td style="text-align: center;"><div class="div7" style="font-family: 'Oswald'">Umamusume</div><span class="dualFont"><%= u.umadexNo() %></span><% if (u.racingSuitNo() != 0) { %><br><div class="div5">(<%= NumberSuffix.addSuffix(u.racingSuitNo()) %> Trainee<br>Umamusume)</div><% } %></td><td style="text-align: left;"><% if (u.parameter() != null) { %><a href="https://umamusume.com/characters/<%= u.parameter() %>"><% } %><%= u.name() == null || u.name().startsWith("(Unknown") ? "&mdash;" : u.name() %><% if (u.parameter() != null) { %></a><% } %></td>
					</tr>
						<% } %>
						<% if (u.umadexNo() > 800) { %>
					<tr>
						<% StringBuilder sb = new StringBuilder(u.name()); %>
						<% int indexOfQuestionMark = u.name().indexOf("?"); %>
						<% if (indexOfQuestionMark >= 0 && indexOfQuestionMark != u.name().length() - 1) { %>
						<%     sb.insert(indexOfQuestionMark + 1, "<br>"); %>
						<% } %>
						<% String nameWith2Lines = sb.toString(); %>
						<td style="text-align: center;" <% if (u.umadexNo() > 800 && u.umadexNo() <= 900) { %>class="div5"<% } %>><%= u.umadexNo() > 800 && u.umadexNo() <= 900 ? "Umamusume<br>as Tracen<br>Academy<br>Affiliate " + (u.umadexNo() - 800) : "<div class=\"div5\">Removed<br>Umamusume</div><span class=\"dualFont\">" + (u.umadexNo() - 900) + "</span>" %></td><td style="text-align: left;"><% if (u.parameter() != null) { %><a href="https://umamusume.com/characters/<%= u.parameter() %>"><% } %><%= u.name() == null || u.name().startsWith("(Unknown") ? "&mdash;" : nameWith2Lines %><% if (u.parameter() != null) { %></a><% } %></td>
					</tr>
						<% } %>
					<% } %>
				</table>
			</div>
			<br>
			<h2>Tracen Academy Affiliate Excluding Umamusume</h2>
			<div class="div9" style="text-align: center;">
				<table>
					<tr>
						<th class="nameOfNotUmamusume">Name</th>
					</tr>
					<% List<NotUmamusumeBean> notUmamusumeList = (List) request.getAttribute("notUmamusumeList"); %>
					<% for (NotUmamusumeBean nu : notUmamusumeList) { %>
					<tr>
						<td style="text-align: left;"><% if (nu.parameter() != null) { %><a href="https://umamusume.com/characters/<%= nu.parameter() %>"><% } %><%= nu.name() == null || nu.name().startsWith("(不明") ? "&mdash;" : nu.name() %><% if (nu.parameter() != null) { %></a><% } %></td>
					</tr>
					<% } %>
				</table>
			</div>
			<br>
			<div class="div4">
			Total Number of Umamusume Excluding "Tracen Academy Affiliate and Removed": <%= noMaxA %><br>
			Total Number of Trainee Umamusume: <%= noMaxB %><br>
			※: The Umadex and Trainee Umadex Excluding "Tracen Academy Affiliate and Removed" are derived from official information provided within the game.<br>
			Sources: [<br>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://umamusume.com/characters/">https://umamusume.com/characters/</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://umamusume.wikiru.jp/index.php?%A5%C6%A1%BC%A5%D6%A5%EB%2F%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%2F%BD%E9%B4%FC%BC%C2%C1%F5">https://umamusume.wikiru.jp/index.php?テーブル/育成ウマ娘/初期実装</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://umamusume.wikiru.jp/index.php?%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%A1%A2%A5%B5%A5%DD%A1%BC%A5%C8%A5%AB%A1%BC%A5%C9%BC%C2%C1%F5%CD%FA%CE%F2">https://umamusume.wikiru.jp/index.php?育成ウマ娘、サポートカード実装履歴</a><br>
			]<br>
			</div>
			<p>Please use this address for inquiries on this website: <a id="mailLink" href="mailto:admin@umamusumelist.com" onclick="showConfirm()">admin@umamusumelist.com</a></p>
			<p>Administrator's Twitter Account: <a href="https://twitter.com/umamusumelist">https://twitter.com/umamusumelist</a></p>
			<button type="button" onclick="location.href='../ja/UmamusumeList'">Japanese Page/日本語版ﾍﾟｰｼﾞ</button>
			<br>
			<span style="display: flex; justify-content: space-between;"><span><button type="button" onclick="location.href='./'">Top Page</button><button type="button" onclick="location.href='./RacingUmamusumeList'">Playable Character List</button></span><span><a href="https://github.com/106-172-232-189/MyWebApp/tree/No.2/">App No.2, Version 4.0</a></span></span>
		</div>
	</body>
</html>