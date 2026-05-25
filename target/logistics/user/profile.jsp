<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.bean.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login.jsp"); return; }
%>
<%@ include file="../includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title">个人中心</h2>
    <% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
    <% if (request.getParameter("error") != null) { %><div class="alert alert-error"><%= request.getParameter("error") %></div><% } %>
    <div class="grid grid-2">
        <div class="card">
            <h3 class="card-title">基本信息</h3>
            <form action="${pageContext.request.contextPath}/user/profile" method="post">
                <div class="form-group"><label>账号</label><input type="text" class="form-control" value="<%= user.getUsername() %>" disabled></div>
                <div class="form-group"><label>昵称</label><input type="text" name="nickname" class="form-control" value="<%= user.getNickname() %>"></div>
                <div class="form-group"><label>手机号</label><input type="text" name="phone" class="form-control" value="<%= user.getPhone() %>"></div>
                <button type="submit" class="btn btn-primary">保存修改</button>
            </form>
        </div>
        <div class="card">
            <h3 class="card-title">修改密码</h3>
            <form action="${pageContext.request.contextPath}/user/profile" method="post">
                <input type="hidden" name="action" value="password">
                <div class="form-group"><label>原密码</label><input type="password" name="oldPassword" class="form-control" required></div>
                <div class="form-group"><label>新密码</label><input type="password" name="newPassword" class="form-control" required></div>
                <button type="submit" class="btn btn-primary">修改密码</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
