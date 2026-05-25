<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% List<Notice> notices = new NoticeDao().findAll(); SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); %>
<%@ include file="../includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title">系统公告</h2>
    <div class="card">
        <% if (notices.isEmpty()) { %><p>暂无公告</p><% } else { %>
        <table class="table">
            <thead><tr><th>标题</th><th>发布时间</th><th>操作</th></tr></thead>
            <tbody>
            <% for (Notice n : notices) { %>
            <tr>
                <td><%= n.getTitle() %></td>
                <td><%= sdf.format(n.getCreateTime()) %></td>
                <td><a href="${pageContext.request.contextPath}/notice/detail.jsp?id=<%= n.getId() %>" class="btn btn-primary btn-sm">查看</a></td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
