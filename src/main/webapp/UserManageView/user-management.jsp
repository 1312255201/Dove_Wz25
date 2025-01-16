<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>用户管理</title>
  <link rel="stylesheet" type="text/css" href="css/usermanagement.css">
</head>
<body>
<h1>用户管理</h1>

<div class="filter-bar">
  <input type="text" id="searchInput" placeholder="输入 steam64id 或 游戏名搜索">
  <button onclick="searchPlayer()">搜索</button>
</div>


<table id="userTable">
  <thead>
  <tr>
    <th>steam64id</th>
    <th>游戏名</th>
    <th>等级</th>
    <th>称号</th>
    <th>积分</th>
    <th>猫粮</th>
    <th>权限组</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody id="tableBody">
  <!-- 动态加载内容 -->
  </tbody>
</table>

<div id="pagination">
  <!-- 分页按钮 -->
</div>

<script src="js/usermanage.js"></script>
</body>
</html>
