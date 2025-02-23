let currentPage = 1; // 当前页码
let currentSearch = ""; // 当前搜索内容

// 加载数据（支持分页和搜索）
function loadPage(page, search = "") {
    currentSearch = search; // 记录搜索条件
    fetch(`LoadPlayerData?page=${page}&search=${encodeURIComponent(search)}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("tableBody");
            tableBody.innerHTML = ""; // 清空旧数据

            if (data.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='8'>未找到符合条件的玩家</td></tr>";
                updatePagination(page);
                return;
            }

            data.forEach(player => {
                const row = `
                    <tr>
                        <td>${player.userid}</td>
                        <td>${player.nickname}</td>
                        <td>${player.level}</td>
                        <td>${player.chenghao}</td>
                        <td>${player.point}</td>
                        <td>${player.catfood}</td>
                        <td>${player.admin}</td>
                        <td>                
                            <button onclick='openEditModal(${JSON.stringify(player)})'>编辑</button>
                            <button onclick='openBanDialog("${player.userid}")'>封禁</button>
                        </td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });

            updatePagination(page);
        });
}

// 搜索玩家
function searchPlayer() {
    const searchInput = document.getElementById("searchInput").value.trim();
    loadPage(1, searchInput); // 搜索时从第一页开始加载
}

// 更新分页按钮
function updatePagination(page) {
    currentPage = page;
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = `
        <button ${page === 1 ? "disabled" : ""} onclick="loadPage(${page - 1}, '${currentSearch}')">上一页</button>
        <button onclick="loadPage(${page + 1}, '${currentSearch}')">下一页</button>
    `;
}

// 初始化加载第一页
loadPage(1);
// 打开编辑模态框
function parseUTCDate(dateString) {
    dateString = dateString.replace(/[\u202F\u00A0]/g, ' '); // 替换特殊空格
    const parts = dateString.split(/[,:\s]+/);
    if (parts.length < 7) return new Date(NaN); // 无效格式处理

    const months = { jan:0, feb:1, mar:2, apr:3, may:4, jun:5,
        jul:6, aug:7, sep:8, oct:9, nov:10, dec:11 };
    const monthStr = parts[0].toLowerCase().substring(0, 3);
    const month = months[monthStr];
    const day = parseInt(parts[1], 10);
    const year = parseInt(parts[2], 10);
    let hour = parseInt(parts[3], 10);
    const minute = parseInt(parts[4], 10);
    const second = parseInt(parts[5], 10);
    const ampm = parts[6].toUpperCase();

    // 转换AM/PM到24小时制
    if (ampm === 'PM' && hour !== 12) hour += 12;
    else if (ampm === 'AM' && hour === 12) hour = 0;

    return new Date(Date.UTC(year, month, day, hour, minute, second));
}
function openEditModal(player) {
    const fields = ["id", "userid", "nickname", "point", "catfood", "catfoodmutiply", "exp", "expmutiply", "level", "killnum", "mvptime", "mvpmusic", "chenghao", "chenghaocolor", "admin", "overtime", "manrenjinfu", "jishayinxiao","jinfuguangbo", "youxian"];
    fields.forEach(field => {
        const input = document.getElementById(field);
        if (input) {
            if (field === "overtime" && player[field]) {
                const date = parseUTCDate(player[field]);
                // 转换为ISO字符串并格式化
                const isoString = date.toISOString();
                input.value = isoString.slice(0, 16).replace('T', ' ');
            } else {
                input.value = player[field] || ""; // 默认值为空字符串
            }
        }
    });
    document.getElementById("editModal").style.display = "block";
}

// 关闭编辑模态框
function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}

// 提交编辑表单
function submitEditUserManage() {
    const form = document.getElementById("editForm");
    const formData = new FormData(form);

    // 转换 FormData 为 JSON
    const data = {};
    formData.forEach((value, key) => {
        if (key === "overtime" ){
            value = formatDate(value);
        }
        if(key === "point")
        {
            if(value === "" || value === undefined)
            {
                value = "0";
            }
        }
        if(key === "catfood")
        {
            if(value === "" || value === undefined)
            {
                value = "0";
            }
        }
        data[key] = value;
    });

    fetch("EditPlayerServlet", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("保存成功！");
                closeEditModal();
                loadPage(currentPage); // 重新加载当前页
            } else {
                alert("保存失败：" + data.message);
            }
        })
        .catch(error => {
            console.error("提交失败:", error);
            alert("提交失败，请稍后重试！");
        });
}

function formatDate(date) {
    const isoString = new Date(date).toISOString(); // 转换为 ISO 8601 格式
    return isoString;  // 例如: "2029-12-31T16:00:00.000Z"
}
function openBanDialog(userId) {
    // 打开封禁对话框并保存玩家ID
    document.getElementById('banDialog').style.display = 'block';
    currentUserId = userId; // 保存当前玩家ID
}

function closeBanDialog() {
    document.getElementById('banDialog').style.display = 'none';
}

function submitBan() {
    const reason = document.getElementById('banReason').value;
    const endTime = document.getElementById('banEndTime').value;

    // 发送封禁请求到后端
    fetch('BanPlayerServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            userid: currentUserId,
            reason: reason,
            endtime: endTime
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("封禁成功");
                closeBanDialog();  // 关闭封禁对话框
            } else {
                alert("封禁失败：" + data.message);
            }
        });
}