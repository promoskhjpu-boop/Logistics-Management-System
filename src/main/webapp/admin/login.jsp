<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="auth-page">
    <div class="auth-card">
        <h2>管理员登录</h2>
        <% if (request.getParameter("error") != null) { %><div class="alert alert-error"><%= request.getParameter("error") %></div><% } %>
        <form action="${pageContext.request.contextPath}/admin/login" method="post">
            <div class="form-group"><label>账号</label><input type="text" name="username" class="form-control" required></div>
            <div class="form-group"><label>密码</label><input type="password" name="password" class="form-control" required></div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:12px;">登录</button>
        </form>
        <div class="auth-links"><a href="${pageContext.request.contextPath}/index.jsp">返回前台</a></div>
    </div>
</div>
</body>
</html>
