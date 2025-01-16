// 确保先创建 XMLHttpRequest 实例
const xhr = new XMLHttpRequest();

// 配置请求方法和目标 URL
xhr.open("GET", "BannedPlayersServlet", true);

// 定义 onreadystatechange 回调函数
xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
            try {
                // 解析返回的 JSON 数据
                const response = JSON.parse(xhr.responseText);
                document.getElementById("playerCount").textContent = `一共封禁了 ${response.total} 名玩家`;

                const scrollList = document.getElementById("scrollList");
                scrollList.innerHTML = ""; // 清空之前的列表

                // 动态插入所有玩家信息
                response.players.forEach(player => {
                    const li = document.createElement("li");
                    li.textContent = `ID: ${player.userid}, 封禁原因: ${player.reason}, 开始时间: ${player.starttime}, 结束时间: ${player.endtime}`;
                    scrollList.appendChild(li);
                });

                // 开启滚动效果
                startScroll();
            } catch (e) {
                console.error("JSON parse error:", e);
            }
        } else {
            console.error("Request failed with status:", xhr.status);
        }
    }
};

// 发送请求
xhr.send();

// 滚动效果函数
function startScroll() {
    const scrollContainer = document.getElementById("scrollContainer");
    const scrollList = document.getElementById("scrollList");
    const scrollItems = scrollList.children;

    if (scrollItems.length > 12) {
        let translateY = 0;
        setInterval(() => {
            translateY -= 10; // 每次滚动 20px
            if (Math.abs(translateY) >= scrollItems.length * 10) {
                translateY = 0; // 回到顶部
            }
            scrollList.style.transform = `translateY(${translateY}px)`;
        }, 300); // 滚动速度（300ms 间隔）
    }
}
