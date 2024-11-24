<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.List,
    		java.util.ArrayList,
    		bean.UmamusumeBean,
    		util.KatakanaToHankaku"%>
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
		<title>ウマ娘一覧 図鑑番号順</title>
		<link rel="stylesheet" href="./css/Style1.css">
		<link rel="icon" href="./favicon.ico">
		<link rel="icon" sizes="192x192" href="./android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon-180x180.png">
	</head>
	<body>
		<button type="button" onclick="location.href='./'">トップページに戻る</button>
		<h1>ウマ娘一覧 図鑑番号順</h1>
		<div>
			<% String id = (String) request.getAttribute("id"); %>
			<% int noMaxA = (int) request.getAttribute("noMaxA"); %>
			<% int noMaxB = (int) request.getAttribute("noMaxB"); %>
			<form action="UmamusumeList" method="post">
				<input type="text" name="id" value="<%= id == null ? "" : id %>" placeholder="公式の図鑑番号もしくは名前" pattern="(^[1-7]?[0-9]{1,2}$)|(^[^\x01-\x7E]{0,30}$)">
				<button type="submit">検索</button>
			</form>
			<br>
			<table>
				<tr>
					<th class="th-sp">図鑑番号<br><div class="div3">(&amp;勝負服)</div></th><th>名前</th>
				</tr>
				<% List<UmamusumeBean> umamusumeList = (List) request.getAttribute("umamusumeList"); %>
				<% for (UmamusumeBean u : umamusumeList) { %>
					<% if (u.noA().intValue() < 800) { %>
					<tr>
						<td style="text-align: center;">No.<%= u.noA() %><% if (u.noB().intValue() != 0) { %><br><div class="div3">(<%= "勝負服" + u.noB() + "号" %>)</div><% } %></td><td><% if (u.parameter() != null) { %><a href="https://umamusume.jp/character/detail/?name=<%= u.parameter() %>"><% } %><%= u.name() == null ? "" : (u.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(u.name()) : u.name()) %><% if (u.parameter() != null) { %></a><% } %></td>
					</tr>
					<% } %>
					<% if (u.noA().intValue() >= 800) { %>
					<tr>
						<td style="text-align: center; <%= u.noA().intValue() >= 800 && u.noA().intValue() < 900 ? "font-size: 12px;" : "" %>"><%= u.noA().intValue() >= 800 && u.noA().intValue() < 900 ? "トレセン学園<br>関係者No." + (u.noA().intValue() - 800) : "没No." + (u.noA().intValue() - 900) %><% if (u.noB().intValue() != 0) { %><br><div style="font-size: 12px;">(<%= "EX勝負服" + u.noB() + "号" %>)<% } %></div></td><td><% if (u.parameter() != null) { %><a href="https://umamusume.jp/character/detail/?name=<%= u.parameter() %>"><% } %><%= u.name() == null ? "" : (u.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(u.name()) : u.name()) %><% if (u.parameter() != null) { %></a><% } %></td>
					</tr>
					<% } %>
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
			出典: [<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服が称号の意味も兼ねていると解釈した根拠: <br class="br-sp"><a href="https://twitter.com/uma_musu/status/1356165415336960013">https://twitter.com/uma_musu/status/<br class="br-sp">1356165415336960013</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;ウマ娘やその勝負服が登録される公式サイトのページ: <br class="br-sp"><a href="https://umamusume.jp/sp/character/">https://umamusume.jp/sp/character/</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ①: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%A5%C6%A1%BC%A5%D6%A5%EB%2F%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%2F%BD%E9%B4%FC%BC%C2%C1%F5">https://umamusume.wikiru.jp/index.php?テーブル/育成ウマ娘/初期実装</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ②: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%A1%A2%A5%B5%A5%DD%A1%BC%A5%C8%A5%AB%A1%BC%A5%C9%BC%C2%C1%F5%CD%FA%CE%F2">https://umamusume.wikiru.jp/index.php?育成ウマ娘、サポートカード実装履歴</a><br>
			]<br>
			</div>
			<p><a class="twitter-timeline" data-lang="ja" data-height="350" href="https://twitter.com/RbSbH9WTaKkBtGd?ref_src=twsrc%5Etfw">管理者のTwitterアカウント</a><script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script></p>
			<input type="button" onclick="location.href='./'" value="トップページに戻る">
		</div>
	</body>
</html>