<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.List,
    		java.util.ArrayList,
    		com.umamusumelist.bean.UmamusumeBean,
    		com.umamusumelist.bean.NotUmamusumeBean,
    		com.umamusumelist.util.KatakanaToHankaku,
			java.time.LocalDateTime,
			java.time.format.DateTimeFormatter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<!-- Google tag (gtag.js) -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-NWMHELKPF9"></script>
		<script>
			window.dataLayer = window.dataLayer || [];
			function gtag(){dataLayer.push(arguments);}
			gtag('js', new Date());
			gtag('config', 'G-NWMHELKPF9');
		</script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width">
		<meta name='description' content='ウマ娘の一覧を追加順で表示しています。図鑑番号＝追加順です。'>
		<title>ウマ娘 キャラクター一覧</title>
		<link rel="stylesheet" href="./css/Style1.css">
		<link rel="stylesheet" href="./css/Style4.css">
		<link rel="icon" href="./favicon.ico">
		<link rel="icon" sizes="192x192" href="./android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon-180x180.png">
		<script src="./js/ShowConfirm.js"></script>
	</head>
	<body>
		<button type="button" onclick="location.href='./'">ﾄｯﾌﾟﾍﾟｰｼﾞへ</button><button type="button" onclick="location.href='./RacingUmamusumeList'">ｷｬﾗｸﾀｰ一覧(実装順)ﾍﾟｰｼﾞへ</button>
		<h1>ウマ娘 キャラクター一覧</h1>
		<img src="./img/Umamusume_Top_Number_Trio.jpg" alt="ポーズをとるトウカイテイオー、スペシャルウィーク、サイレンススズカ" class="imgB" style="display: block; margin: auto;">
		<div class="div4" style="text-align: center;">©Cygames</div>
		<br>
		<div>
			<% String id = (String) request.getAttribute("id"); %>
			<% int noMaxA = (int) request.getAttribute("noMaxA"); %>
			<% int noMaxB = (int) request.getAttribute("noMaxB"); %>
			<form action="UmamusumeList" method="post">
				<input type="text" name="id" value="<%= id == null ? "" : id %>" placeholder="公式の図鑑番号もしくは名前" title="公式の図鑑番号もしくは名前/&quot;3桁までの数字&quot;もしくは&quot;20桁までのカタカナ(一部を除く)&quot;" size="26" pattern="(^[0-9０-９]{1,3}$)|(^[^\x01-\x7E]{0,20}$)">
				<button type="submit">検索</button>
			</form>
			<br>
			<table>
				<tr>
					<th class="th-sp">図鑑番号<br><div class="div3">(&amp;勝負服)</div></th><th>名前</th>
				</tr>
				<% List<UmamusumeBean> umamusumeList = (List) request.getAttribute("umamusumeList"); %>
				<% for (UmamusumeBean u : umamusumeList) { %>
					<% if (u.umadexNo() < 800) { %>
				<tr>
					<td style="text-align: center;">No.<%= u.umadexNo() %><% if (u.racingSuitNo() != 0) { %><br><div class="div3">(<%= "勝負服" + u.racingSuitNo() + (u.racingSuitNo() >= 100 ? "" : "号") %>)</div><% } %></td><td><% if (u.parameter() != null) { %><a href="https://umamusume.jp/character/<%= u.parameter() %>"><% } %><%= u.name() == null || u.name().startsWith("(不明") ? "&mdash;" : (u.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(u.name()) : u.name()) %><% if (u.parameter() != null) { %></a><% } %></td>
				</tr>
					<% } %>
					<% if (u.umadexNo() >= 800) { %>
				<tr>
					<td style="text-align: center;" <% if (u.umadexNo() >= 800 && u.umadexNo() < 900) { %>class="div5"<% } %>><%= u.umadexNo() >= 800 && u.umadexNo() < 900 ? "トレセン学園<br>関係者No." + (u.umadexNo() - 800) : "没No." + (u.umadexNo() - 900) %><% if (u.racingSuitNo() != 0) { %><br><div class="div5">(<%= "EX勝負服" + u.racingSuitNo() %>)<% } %></div></td><td><% if (u.parameter() != null) { %><a href="https://umamusume.jp/character/<%= u.parameter() %>"><% } %><%= u.name() == null || u.name().startsWith("(不明") ? "&mdash;" : (u.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(u.name()) : u.name()) %><% if (u.parameter() != null) { %></a><% } %></td>
				</tr>
					<% } %>
				<% } %>
			</table>
			<br>
			<h2>ウマ娘でないトレセン学園関係者</h2>
			<table>
				<tr>
					<th class="nameOfNotUmamusume">名前</th>
				</tr>
				<% List<NotUmamusumeBean> notUmamusumeList = (List) request.getAttribute("notUmamusumeList"); %>
				<% for (NotUmamusumeBean nu : notUmamusumeList) { %>
				<tr>
					<td><% if (nu.parameter() != null) { %><a href="https://umamusume.jp/character/<%= nu.parameter() %>"><% } %><%= nu.name() == null || nu.name().startsWith("(不明") ? "&mdash;" : nu.name() %><% if (nu.parameter() != null) { %></a><% } %></td>
				</tr>
				<% } %>
			</table>
			<br>
			<div class="div4">
			トレセン学園関係者であるウマ娘、没ウマ娘を除いたウマ娘の総数: <%= noMaxA %><br>
			勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数: <%= noMaxB %><br>
			図鑑番号の基本的な意味: 公式サイトのウマ娘紹介ページに登録された順番<br>
			図鑑に登録される条件: 公式の立ち絵がある<br>
			勝負服の定義: URAがウマ娘に対して発行する、競走(特にGI競走)にて着用する衣装、もしくは称号<br>
			勝負服番号の基本的な意味: 公式サイトのウマ娘紹介ページで勝負服が登録された順番<br>
			公式サイトのウマ娘紹介ページで勝負服が登録される基本的な条件: 新たに育成ウマ娘として実装され、かつ衣装違いではない<br>
			※: 図鑑番号はトレセン学園関係者であるウマ娘の番号及び没ウマ娘の番号を除いて公式です。<br>
			※※: 勝負服番号はEX勝負服を除いて公式です。<br>
			※※※: 図鑑に登録されていないウマ娘については、<a href="https://twitter.com/RbSbH9WTaKkBtGd/status/1715564824858624044">こちらのポスト/ツイート</a>を参照してください。<br>
			出典: [<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服が称号の意味も兼ねていると解釈した根拠: <br class="br-sp"><a href="https://twitter.com/uma_musu/status/1356165415336960013">https://twitter.com/uma_musu/status/<br class="br-sp">1356165415336960013</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;ウマ娘やその勝負服が登録される公式サイトのページ: <br class="br-sp"><a href="https://umamusume.jp/character/">https://umamusume.jp/character/</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ①: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%A5%C6%A1%BC%A5%D6%A5%EB%2F%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%2F%BD%E9%B4%FC%BC%C2%C1%F5">https://umamusume.wikiru.jp/index.php?テーブル/育成ウマ娘/初期実装</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ②: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%A1%A2%A5%B5%A5%DD%A1%BC%A5%C8%A5%AB%A1%BC%A5%C9%BC%C2%C1%F5%CD%FA%CE%F2">https://umamusume.wikiru.jp/index.php?育成ウマ娘、サポートカード実装履歴</a><br>
			]<br>
			</div>
			<p>↓管理者からのお願い↓</p>
			<blockquote class="twitter-tweet tw-align-center"><p lang="ja" dir="ltr">もしも<a href="https://umamusume.jp/character/">ｳﾏ娘公式ｻｲﾄ</a>に登録されているウマ娘の<br><a href="http://umamusumelist.com/UmamusumeList">当サイトの図鑑番号順ページ</a><br>への登録漏れがありましたら、私に報告をお願い<br>します。すぐに修正登録を行います。<a href="https://twitter.com/hashtag/%E3%82%A6%E3%83%9E%E5%A8%98?src=hash&amp;ref_src=twsrc%5Etfw">#ウマ娘</a></p>&mdash; むっぎー (@RbSbH9WTaKkBtGd) <a href="https://twitter.com/RbSbH9WTaKkBtGd/status/1779827006269714681">April 15, 2024</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
			<p>連絡は<a id="mailLink" href="mailto:admin@umamusumelist.com" onclick="showConfirm()">admin@umamusumelist.com</a><br class="br-sp4">までお願いします。</p>
			<br>
			<span style="display: flex; justify-content: space-between;"><span><input type="button" onclick="location.href='./'" value="ﾄｯﾌﾟﾍﾟｰｼﾞへ"><input type="button" onclick="location.href='./RacingUmamusumeList'" value="ｷｬﾗｸﾀｰ一覧(実装順)ﾍﾟｰｼﾞへ"></span><span>Version 5.2</span></span>
		</div>
	</body>
</html>
<%
String xffh = request.getHeader("X-Forwarded-For");
System.out.println(
	(xffh == null ? request.getRemoteAddr() : xffh.split(",")[0].trim()) + " " +
	LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'(UTC+09:00)'")) + " " +
	request.getMethod() +
	" /UmamusumeList"
);
%>