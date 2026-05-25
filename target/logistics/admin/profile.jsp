<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>修改密码</h1></div>
<% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
<% if (request.getParameter("error") != null) { %><div class="alert alert-error"><%= request.getParameter("error") %></div><% } %>
<div class="admin-card" style="max-width:480px;">
    <form action="${pageContext.request.contextPath}/admin/profile" method="post">
        <div class="form-group"><label>原密码</label><input type="password" name="oldPassword" class="form-control" required></div>
        <div class="form-group"><label>新密码</label><input type="password" name="newPassword" class="form-control" required></div>
        <button type="submit" class="btn btn-primary">保存</button>
    </form>
</div>
<%@ include file="/admin/includes/footer.jsp" %>
