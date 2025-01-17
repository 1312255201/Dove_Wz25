<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>封禁管理</title>
  <link rel="stylesheet" type="text/css" href="css/banmanagement.css">
</head>
<body>
<h1>封禁管理</h1>

<div class="filter-bar">
  <input type="text" id="searchInput" placeholder="输入 steam64id 搜索">
  <button onclick="searchBan()">搜索</button>
</div>


<table id="userTable">
  <thead>
  <tr>
    <th>Steam64ID</th>
    <th>原因</th>
    <th>封禁时间</th>
    <th>结束时间</th>
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

<!-- 编辑模态框 -->
<div id="editModal" style="display:none; position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); background:white; padding:20px; border-radius:8px; box-shadow:0 4px 8px rgba(0, 0, 0, 0.2);">
  <form id="editForm">
    <div>
      <label for="reason">封禁原因:</label>
      <input type="text" id="reason" name="reason" required>
    </div>
    <div>
      <label for="endtime">封禁结束时间:</label>
      <input type="datetime-local" id="endtime" name="endtime" required>
    </div>
    <button type="button" onclick="submitEditBanManage()">保存</button>
    <button type="button" onclick="closeEditModal()">取消</button>
  </form>
</div>
<script src="js/banmanage.js"></script>
</body>
</html>
