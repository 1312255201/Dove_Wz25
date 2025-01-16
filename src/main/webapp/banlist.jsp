<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2025/1/14
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>鸽子社区封神榜</title>
  <link rel="stylesheet" type="text/css" href="css/fengshenbang.css">
</head>
<body>
<div class="banner-container">
  <h1>鸽子社区封神榜</h1>
  <h2>净化服务器环境我们是认真的</h2>
  <p id="playerCount">加载中...</p>
  <div id="scrollContainer" style="overflow: hidden; height: 120px;">
    <ul id="scrollList" style="list-style: none; padding: 0; margin: 0;">
      <!-- 滚动条目将在这里动态插入 -->
    </ul>
  </div>

</div>

<script src="js/banlist.js">

</script>

</body>
</html>
