<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>

<div class="auth-page">
    <div class="auth-card">
        <h2>用户登录</h2>
        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-error"><%= request.getParameter("error") %></div>
        <% } %>
        <% if (request.getParameter("msg") != null) { %>
        <div class="alert alert-success"><%= request.getParameter("msg") %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/user/login" method="post">
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="username" class="form-control" required placeholder="请输入账号">
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" class="form-control" required placeholder="请输入密码">
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:12px;">登录</button>
        </form>
        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/register.jsp">没有账号？立即注册</a>
            &nbsp;|&nbsp;
            <a href="${pageContext.request.contextPath}/forgot-password.jsp">忘记密码</a>
        </div>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>
