let currentPage = 1; // 当前页码
let currentSearch = ""; // 当前搜索内容

// 加载数据（支持分页和搜索）
function loadPage(page, search = "") {
    currentSearch = search; // 记录搜索条件
    fetch(`LoadBanData?page=${page}&search=${encodeURIComponent(search)}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("tableBody");
            tableBody.innerHTML = ""; // 清空旧数据

            if (data.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='8'>未找到符合条件的记录</td></tr>";
                updatePagination(page);
                return;
            }

            data.forEach(ban => {
                const row = `
                    <tr>
                        <td>${ban.id}</td>
                        <td>${ban.userid}</td>
                        <td>${ban.reason}</td>
                        <td>${ban.starttime}</td>
                        <td>${ban.endtime}</td>
                        <td>                
                            <button onclick='openEditModal(${JSON.stringify(ban)})'>编辑</button>
                            <button onclick='deleteBanInfo("${ban.id}")'>删除</button>
                        </td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });

            updatePagination(page);
        });
}

// 搜索玩家
function searchBan() {
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

function openEditModal(ban) {
    const fields = ["id", "userid", "reason", "starttime", "endtime"];
    fields.forEach(field => {
        const input = document.getElementById(field);
        if (input) {
            if (field === "starttime" || field === "endtime") {
                if (ban[field]) {
                    const date = parseUTCDate(ban[field]);
                    // 转换为ISO字符串并格式化
                    const isoString = date.toISOString();
                    input.value = isoString.slice(0, 16).replace('T', ' ');
                } else {
                    input.value = ""; // 处理空值
                }
            } else {
                input.value = ban[field] || "";
            }
        }
    });
    document.getElementById("editModal").style.display = "block";
}

function deleteBanInfo(id){
    fetch("DeleteBanServlet", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({'id':id}),
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("删除成功！");
                closeEditModal();
                loadPage(currentPage); // 重新加载当前页
            } else {
                alert("删除失败：" + data.message);
            }
        })
        .catch(error => {
            console.error("提交失败:", error);
            alert("提交失败，请稍后重试！");
        });

}

// 关闭编辑模态框
function closeEditModal() {
    document.getElementById("editModal").style.display = "none";
}

// 提交编辑表单
function submitEditBanManage() {
    const form = document.getElementById("editForm");
    const formData = new FormData(form);

    // 转换 FormData 为 JSON
    const data = {};
    formData.forEach((value, key) => {
        if (key === "endtime" ){
            value = formatDate(value);
        }
        data[key] = value;
    });

    fetch("EditBanServlet", {
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
