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
                            <button class="edit-btn" onclick="openEditModal('${player.userid}', '${player.nickname}', '${player.level}', '${player.point}', '${player.catfood}', '${player.admin}')">编辑</button>
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
