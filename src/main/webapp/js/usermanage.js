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
function openEditModal(player) {
    const fields = ["id", "userid", "nickname", "point", "catfood", "catfoodmutiply", "exp", "expmutiply", "level", "killnum", "mvptime", "mvpmusic", "chenghao", "chenghaocolor", "admin", "overtime", "manrenjinfu", "jishayinxiao","jinfuguangbo", "youxian"];
    fields.forEach(field => {
        const input = document.getElementById(field);
        if (input) {
            if (field === "overtime" && player[field]) {
                // 格式化时间为 datetime-local 格式
                const date = new Date(player[field]);
                input.value = date.toISOString().slice(0, 16); // yyyy-MM-ddTHH:mm
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
