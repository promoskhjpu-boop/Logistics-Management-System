function copyText(text) {
    if (navigator.clipboard) {
        navigator.clipboard.writeText(text).then(function() {
            alert('已复制到剪贴板');
        });
    } else {
        var input = document.createElement('input');
        input.value = text;
        document.body.appendChild(input);
        input.select();
        document.execCommand('copy');
        document.body.removeChild(input);
        alert('已复制到剪贴板');
    }
}

function saveQueryHistory(trackingNo, companyName, companyId) {
    var history = JSON.parse(localStorage.getItem('queryHistory') || '[]');
    var item = {
        trackingNo: trackingNo,
        companyName: companyName || '',
        companyId: companyId || '',
        time: new Date().toLocaleString('zh-CN', { hour12: false })
    };
    history = history.filter(function(h) { return h.trackingNo !== trackingNo; });
    history.unshift(item);
    if (history.length > 10) history = history.slice(0, 10);
    localStorage.setItem('queryHistory', JSON.stringify(history));
}

function renderQueryHistory(containerId, contextPath) {
    var container = document.getElementById(containerId);
    if (!container) return;
    var history = JSON.parse(localStorage.getItem('queryHistory') || '[]');
    if (history.length === 0) {
        container.innerHTML = '<div class="home-history-empty"><span>📭</span><p>暂无查询记录</p><small>查询后将自动保存在此</small></div>';
        return;
    }
    var html = '<ul class="home-history-list">';
    history.forEach(function(h) {
        var queryUrl = contextPath + '/track/query?trackingNo=' + encodeURIComponent(h.trackingNo);
        if (h.companyId) queryUrl += '&companyId=' + h.companyId;
        html += '<li class="home-history-item">';
        html += '<a href="' + queryUrl + '" class="home-history-main">';
        html += '<span class="home-history-no">' + h.trackingNo + '</span>';
        html += '<span class="home-history-company">' + (h.companyName || '未知公司') + '</span>';
        html += '</a>';
        html += '<time class="home-history-time">' + h.time + '</time>';
        html += '</li>';
    });
    html += '</ul>';
    container.innerHTML = html;
}

function confirmDelete(msg) {
    return confirm(msg || '确定要删除吗？');
}
