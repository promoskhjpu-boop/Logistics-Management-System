<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${not empty pageTitle ? pageTitle : '快递管理系统'}" /> - 快递管理系统</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<header class="site-header">
    <div class="container header-inner">
        <a href="<%= request.getContextPath() %>/index.jsp" class="logo">
            <span class="logo-icon">📮</span>
            快递<span>管理系统</span>
        </a>
        <nav>
            <ul class="nav-links">
                <li><a href="<%= request.getContextPath() %>/index.jsp" class="nav-link">首页</a></li>
                <li><a href="<%= request.getContextPath() %>/company/list.jsp" class="nav-link">快递公司</a></li>
                <li><a href="<%= request.getContextPath() %>/notice/list.jsp" class="nav-link">公告</a></li>
                <li><a href="<%= request.getContextPath() %>/help.jsp" class="nav-link">帮助中心</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="<%= request.getContextPath() %>/user/my-express.jsp" class="nav-link">我的快递</a></li>
                </c:if>
            </ul>
        </nav>
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <span class="user-greeting">你好，${sessionScope.user.nickname}</span>
                    <a href="<%= request.getContextPath() %>/user/profile.jsp" class="btn btn-outline btn-sm">个人中心</a>
                    <a href="<%= request.getContextPath() %>/user/logout" class="btn btn-ghost btn-sm">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="<%= request.getContextPath() %>/login.jsp" class="btn btn-outline btn-sm">登录</a>
                    <a href="<%= request.getContextPath() %>/register.jsp" class="btn btn-primary btn-sm">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
