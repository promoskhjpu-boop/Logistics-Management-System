<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>

<div class="auth-page">
    <div class="auth-card">
        <h2>用户注册</h2>
        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-error"><%= request.getParameter("error") %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/user/register" method="post">
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="username" class="form-control" required placeholder="请输入账号">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" class="form-control" required placeholder="请输入密码">
            </div>
            <div class="form-group">
                <label>昵称</label>
                <input type="text" name="nickname" class="form-control" placeholder="请输入昵称">
            </div>
            <div class="form-group">
                <label>手机号</label>
                <input type="text" name="phone" class="form-control" placeholder="请输入手机号">
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:12px;">注册</button>
        </form>
        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/login.jsp">已有账号？立即登录</a>
        </div>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>
