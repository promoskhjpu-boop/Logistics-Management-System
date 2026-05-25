<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%
    int userCount = new UserDao().count();
    int expressCount = new ExpressDao().count();
    int companyCount = new CompanyDao().count();
    int queryToday = new QueryLogDao().countToday();
    int pending = new ExpressDao().countByStatus(0);
    int transit = new ExpressDao().countByStatus(1);
    int delivering = new ExpressDao().countByStatus(2);
    int signed = new ExpressDao().countByStatus(3);
%>
<%@ include file="/admin/includes/header.jsp" %>

<div class="admin-header"><h1>数据统计</h1></div>
<div class="stat-cards">
    <div class="stat-card"><div class="label">注册用户</div><div class="value"><%= userCount %></div></div>
    <div class="stat-card"><div class="label">快递总量</div><div class="value"><%= expressCount %></div></div>
    <div class="stat-card"><div class="label">快递公司</div><div class="value"><%= companyCount %></div></div>
    <div class="stat-card"><div class="label">今日查询</div><div class="value"><%= queryToday %></div></div>
</div>
<div class="admin-card">
    <h3>快递状态分布</h3>
    <table class="table">
        <tr><td>待揽收</td><td><%= pending %></td></tr>
        <tr><td>运输中</td><td><%= transit %></td></tr>
        <tr><td>派送中</td><td><%= delivering %></td></tr>
        <tr><td>已签收</td><td><%= signed %></td></tr>
    </table>
</div>
<%@ include file="/admin/includes/footer.jsp" %>
