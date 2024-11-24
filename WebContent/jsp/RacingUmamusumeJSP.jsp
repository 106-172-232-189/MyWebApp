<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,
			java.util.ArrayList,
			com.umamusumelist.bean.RacingUmamusumeBean,
			com.umamusumelist.util.KatakanaToHankaku"%>
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
		<meta name='description' content='ウマ娘の一覧を実装順で表示しています。勝負服番号＝実装順です。'>
		<title>ウマ娘 キャラクター一覧(実装順)</title>
		<link rel="stylesheet" href="./css/Style1.css">
		<link rel="stylesheet" href="./css/Style2.css">
		<link rel="icon" href="./favicon.ico">
		<link rel="icon" sizes="192x192" href="./android-touch-icon-192x192.png">
		<link rel="apple-touch-icon" sizes="180x180" href="./apple-touch-icon-180x180.png">
		<script src="./js/ShowConfirm.js"></script>
	</head>
	<body>
		<button type="button" onclick="location.href='./'">トップページに戻る</button>
		<h1>ウマ娘 キャラクター一覧(実装順)</h1>
		<img src="./img/Umamusume_Top_Number_Trio.jpg" alt="ポーズをとるトウカイテイオー、スペシャルウィーク、サイレンススズカ" class="imgB" style="display: block; margin: auto;">
		<div class="div4" style="text-align: center;">©Cygames</div>
		<br>
		<div>
			<% String id = (String) request.getAttribute("id"); %>
			<% int noMax = (int) request.getAttribute("noMax"); %>
			<form action="RacingUmamusumeList" method="post">
				<input type="text" name="id" value="<%= id == null ? "" : id %>" placeholder="公式の勝負服番号もしくは名前" title="公式の勝負服番号もしくは名前/3桁までの数字もしくは20桁までのカタカナ" size="24" pattern="(^[0-9０-９]{1,3}$)|(^[ァ-ヴーｦ-ﾟ]{0,20}$)">
				<button type="submit">検索</button>
			</form>
			<br>
			<table>
				<tr class="tr-sp">
					<th colspan="2">勝負服</th><th>名前</th><th><div class="div6">勝負服登録日</div></th>
				</tr>
				<% List<RacingUmamusumeBean> racingUmamusumeList = (List) request.getAttribute("racingUmamusumeList"); %>
				<% List<RacingUmamusumeBean> racingUmamusumeListExclusive = (List) request.getAttribute("racingUmamusumeListExclusive"); %>
				<% for (RacingUmamusumeBean ru : racingUmamusumeList) { %>
				<tr class="tr-sp">
					<td>勝負服</td><td><%= ru.racingSuitNo() %></td><td><% if (ru.parameter() != null) { %><a href="https://umamusume.jp/character/<%= ru.parameter() %>"><% } %><%= ru.name() == null || ru.name().startsWith("(不明") ? "&mdash;" : (ru.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(ru.name()) : ru.name()) %><% if (ru.parameter() != null) { %></a><% } %></td><td><div class="div6"><%= ru.appeared() == null ? "-" : ru.appeared() %></div></td>
				</tr>
				<% } %>
				<script>
					const txtArray = [];
				</script>
				<% for (RacingUmamusumeBean ru : racingUmamusumeListExclusive) { %>
				<tr class="tr-sp">
					<script>
						txtArray.push([['<span class="span-a">EX</span><br><span class="span-b">勝負服</span>', <%= ru.racingSuitNo() %>], ['勝負服', <%= 900 + ru.racingSuitNo() %>]]);
					</script>
					<td><span class="span-c"><span class="span-a">EX</span><br><span class="span-b">勝負服</span></span></td><td><span class="span-d"><%= ru.racingSuitNo() %></span></td><td><% if (ru.parameter() != null) { %><a href="https://umamusume.jp/character/<%= ru.parameter() %>"><% } %><%= ru.name() == null || ru.name().startsWith("(不明") ? "&mdash;" : (ru.name().contains("(") ? KatakanaToHankaku.katakanaToHankaku(ru.name()) : ru.name()) %><% if (ru.parameter() != null) { %></a><% } %></td><td><div class="div6"><%= ru.appeared() == null ? "-" : ru.appeared() %></div></td>
				</tr>
				<% } %>
			</table>
			<script>
				var j = 1;
				setInterval(function () {
					if (txtArray.length > 0) {
						const spanC = document.getElementsByClassName('span-c');
						const spanD = document.getElementsByClassName('span-d');
						for (var i = 0; i < spanC.length; i++) {
							spanC[i].innerHTML = txtArray[i][j][0];
							spanD[i].innerHTML = txtArray[i][j][1];
						}
						j = (j + 1) % txtArray[0].length;
					}
				}, 5000);
			</script>
			<br>
			<div class="div4">
			勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数: <%= noMax %><br>
			勝負服の定義: URAがウマ娘に対して発行する、競走(特にGI競走)にて着用する衣装、もしくは称号<br>
			勝負服番号の基本的な意味: 公式サイトのウマ娘紹介ページで勝負服が登録された順番<br>
			勝負服順リストに登録される条件: 公式の立ち絵があり、かつ公式サイトのウマ娘紹介ページで勝負服が登録される<br>
			EX勝負服順リストに登録される条件: 公式の立ち絵はあるが、公式サイトのウマ娘紹介ページで勝負服が登録されず、かつ育成ウマ娘としての実装の見込みがほとんどない<br>
			公式サイトのウマ娘紹介ページで勝負服が登録される条件: 新たに育成ウマ娘として実装され、かつ衣装違いではない<br>
			※: 勝負服番号はEX勝負服を除いて公式です。<br>
			出典: [<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服が称号の意味も兼ねていると解釈した根拠: <br class="br-sp"><a href="https://twitter.com/uma_musu/status/1356165415336960013">https://twitter.com/uma_musu/status/<br class="br-sp">1356165415336960013</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服が登録される公式サイトのページ: <br class="br-sp"><a href="https://umamusume.jp/character/">https://umamusume.jp/character/</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ①: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%A5%C6%A1%BC%A5%D6%A5%EB%2F%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%2F%BD%E9%B4%FC%BC%C2%C1%F5">https://umamusume.wikiru.jp/index.php?テーブル/育成ウマ娘/初期実装</a>,<br>
			&nbsp;&nbsp;&nbsp;&nbsp;勝負服番号を確認する際に参考にしたページ②: <br class="br-sp"><a href="https://umamusume.wikiru.jp/index.php?%B0%E9%C0%AE%A5%A6%A5%DE%CC%BC%A1%A2%A5%B5%A5%DD%A1%BC%A5%C8%A5%AB%A1%BC%A5%C9%BC%C2%C1%F5%CD%FA%CE%F2">https://umamusume.wikiru.jp/index.php?育成ウマ娘、サポートカード実装履歴</a><br>
			]<br>
			</div>
			<p>↓管理者からのお願い↓</p>
			<blockquote class="twitter-tweet tw-align-center"><p lang="ja" dir="ltr">もしも<a href="https://umamusume.jp/character/">ウマ娘公式サイト</a><br>にて「新規育成ウマ娘が発表される前に勝負服が<br>登録された」ことを確認次第、私に報告をお願い<br>します。すぐに<a href="http://umamusumelist.com/RacingUmamusumeList">当ｻｲﾄの勝負服番号順/実装順ﾍﾟｰｼﾞ</a><br>にて暫定登録を行います。<a href="https://twitter.com/hashtag/%E3%82%A6%E3%83%9E%E5%A8%98?src=hash&amp;ref_src=twsrc%5Etfw">#ウマ娘</a></p>&mdash; むっぎー (@RbSbH9WTaKkBtGd) <a href="https://twitter.com/RbSbH9WTaKkBtGd/status/1779827257986703690">April 15, 2024</a></blockquote> <script async src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
			<p>連絡は<a id="mailLink" href="mailto:admin@umamusumelist.com" onclick="showConfirm()">admin@umamusumelist.com</a><br class="br-sp4">までお願いします。</p>
			<br>
			<input type="button" onclick="location.href='./'" value="トップページに戻る">
		</div>
	</body>
</html>