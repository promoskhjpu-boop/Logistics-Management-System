<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<QueryLog> queryLogs = new QueryLogDao().findRecent(50);
    List<LoginLog> loginLogs = new LoginLogDao().findRecent(50);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>系统日志</h1></div>
<div class="admin-card">
    <h3>查询记录</h3>
    <table class="table">
        <thead><tr><th>单号</th><th>用户</th><th>公司</th><th>IP</th><th>时间</th></tr></thead>
        <tbody>
        <% for (QueryLog q : queryLogs) { %>
        <tr>
            <td><%= q.getTrackingNo() %></td>
            <td><%= q.getUsername() != null ? q.getUsername() : "游客" %></td>
            <td><%= q.getCompanyName() != null ? q.getCompanyName() : "-" %></td>
            <td><%= q.getIp() %></td>
            <td><%= sdf.format(q.getQueryTime()) %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<div class="admin-card">
    <h3>登录记录</h3>
    <table class="table">
        <thead><tr><th>类型</th><th>账号</th><th>IP</th><th>结果</th><th>时间</th></tr></thead>
        <tbody>
        <% for (LoginLog l : loginLogs) { %>
        <tr>
            <td><%= l.getUserTypeText() %></td>
            <td><%= l.getUsername() %></td>
            <td><%= l.getIp() %></td>
            <td><%= l.getSuccess()==1?"成功":"失败" %></td>
            <td><%= sdf.format(l.getLoginTime()) %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<%@ include file="/admin/includes/footer.jsp" %>
