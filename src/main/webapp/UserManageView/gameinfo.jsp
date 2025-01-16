<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2025/1/16
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>玩家信息</title>
    <link rel="stylesheet" type="text/css" href="css/gameinfo.css">
</head>
<body>
<div class="info-container">
    <h1>玩家信息</h1>
    <div id="playerInfo">
        <p>加载中...</p>
    </div>
</div>

<script>
    // 使用 AJAX 加载玩家信息
    function loadPlayerInfo() {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "PlayerInfoServlet", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById("playerInfo").innerHTML = xhr.responseText;
            }
        };
        xhr.send();
    }

    // 页面加载时调用
    window.onload = loadPlayerInfo;
</script>

</body>
</html>
