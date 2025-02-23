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
    <th>Steam64ID</th>
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

<!-- 编辑模态框 -->
<div id="editModal" style="display:none; position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); background:white; padding:20px; border-radius:8px; box-shadow:0 4px 8px rgba(0, 0, 0, 0.2);">
  <form id="editForm">
    <input type="hidden" id="id" name="id">
    <div>
      <label for="userid">Steam64ID:</label>
      <input type="text" id="userid" name="userid" readonly>
    </div>
    <div>
      <label for="nickname">游戏名:</label>
      <input type="text" id="nickname" name="nickname" readonly>
    </div>
    <div>
      <label for="point">积分:</label>
      <input type="text" id="point" name="point" required>
    </div>
    <div>
      <label for="catfood">猫粮:</label>
      <input type="text" id="catfood" name="catfood" required>
    </div>
    <div>
      <label for="catfoodmutiply">猫粮倍数:</label>
      <input type="number" id="catfoodmutiply" name="catfoodmutiply" required>
    </div>
    <div>
      <label for="exp">经验:</label>
      <input type="text" id="exp" name="exp" required>
    </div>
    <div>
      <label for="expmutiply">经验倍数:</label>
      <input type="number" id="expmutiply" name="expmutiply" required value="1" onfocus="this.value = ''" onblur="if(this.value === '') { this.value = '1'; }">
    </div>
    <div>
      <label for="level">等级:</label>
      <input type="text" id="level" name="level" required>
    </div>
    <div>
      <label for="killnum">总击杀数:</label>
      <input type="text" id="killnum" name="killnum" readonly>
    </div>
    <div>
      <label for="mvptime">MVP次数:</label>
      <input type="text" id="mvptime" name="mvptime" readonly>
    </div>
    <div>
      <label for="mvpmusic">MVP音乐盒:</label>
      <input type="text" id="mvpmusic" name="mvpmusic" required>
    </div>
    <div>
      <label for="chenghao">称号:</label>
      <input type="text" id="chenghao" name="chenghao" required>
    </div>
    <div>
      <label for="chenghaocolor">称号颜色:</label>
      <input type="text" id="chenghaocolor" name="chenghaocolor" required>
    </div>
    <div>
      <label for="admin">权限组:</label>
      <input type="text" id="admin" name="admin" required>
    </div>
    <div>
      <label for="overtime">权限组过期时间:</label>
      <input type="datetime-local" id="overtime" name="overtime" required>
    </div>
    <div>
      <label for="manrenjinfu">满人进服:</label>
      <input type="text" id="manrenjinfu" name="manrenjinfu" required>
    </div>
    <div>
      <label for="jishayinxiao">击杀音效:</label>
      <input type="text" id="jishayinxiao" name="jishayinxiao" required>
    </div>
    <div>
      <label for="jinfuguangbo">进服广播:</label>
      <input type="text" id="jinfuguangbo" name="jinfuguangbo" required>
    </div>
    <div>
      <label for="youxian">优先:</label>
      <input type="text" id="youxian" name="youxian" required>
    </div>
    <button type="button" onclick="submitEditUserManage()">保存</button>
    <button type="button" onclick="closeEditModal()">取消</button>
  </form>
</div>
<!-- 弹出封禁对话框 -->
<div id="banDialog" style="display:none; position:fixed; top:50%; left:50%; transform:translate(-50%, -50%); background:white; padding:20px; border-radius:8px; box-shadow:0 4px 8px rgba(0, 0, 0, 0.2);">
  <h3>封禁玩家</h3>
  <label for="banReason">封禁原因:</label>
  <input type="text" id="banReason" placeholder="请输入封禁原因"><br>
  <label for="banEndTime">封禁结束时间:</label>
  <input type="datetime-local" id="banEndTime"><br>
  <button onclick="submitBan()">提交封禁</button>
  <button onclick="closeBanDialog()">关闭</button>
</div>
<script src="js/usermanage.js"></script>
</body>
</html>
