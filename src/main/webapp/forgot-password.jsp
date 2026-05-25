<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>

<div class="auth-page">
    <div class="auth-card">
        <h2>找回密码</h2>
        <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-error"><%= request.getParameter("error") %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/reset-password" method="post">
            <div class="form-group">
                <label>账号</label>
                <input type="text" name="username" class="form-control" required>
            </div>
            <div class="form-group">
                <label>注册手机号</label>
                <input type="text" name="phone" class="form-control" required>
            </div>
            <div class="form-group">
                <label>新密码</label>
                <input type="password" name="newPassword" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:12px;">重置密码</button>
        </form>
        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/login.jsp">返回登录</a>
        </div>
    </div>
</div>

<%@ include file="includes/footer.jsp" %>
