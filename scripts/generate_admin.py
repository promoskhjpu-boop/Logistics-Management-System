# -*- coding: utf-8 -*-
import os

D = 'd' + 'iv'
base = r'd:\Logistics-Management-System\src\main\webapp'

def write(rel, content):
    path = os.path.join(base, rel.replace('/', os.sep))
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print('wrote', rel)

sidebar = f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="sidebar-nav">
    <li><a href="${{pageContext.request.contextPath}}/admin/index.jsp">数据统计</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/express/list.jsp">快递管理</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/company/list.jsp">物流公司</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/user/list.jsp">用户管理</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/notice/list.jsp">公告管理</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/logs.jsp">系统日志</a></li>
    <li><a href="${{pageContext.request.contextPath}}/admin/profile.jsp">修改密码</a></li>
    <li><a href="${{pageContext
Context.request.contextPath}}/admin/logout">退出登录</a></li>
</ul>'''

# fix typo in sidebar - pageContext
sidebar = sidebar.replace('page\nContext', 'pageContext')

write('admin/includes/sidebar.jsp', sidebar)

admin_head = '''<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台管理 - 快递管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <aside class="admin-sidebar">
        <motion class="sidebar-brand">快递管理后台</motion>
        <%@ include file="/admin/includes/sidebar.jsp" %>
    </aside>
    <main class="admin-main">
'''.replace('motion', D)

write('admin/includes/header.jsp', admin_head)

write('admin/includes/footer.jsp', f'''</main>
</{D}>
</body>
</html>
''')

write('admin/login.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <link rel="stylesheet" href="${{pageContext.request.contextPath}}/css/style.css">
</head>
<body>
<{D} class="auth-page">
    <{D} class="auth-card">
        <h2>管理员登录</h2>
        <% if (request.getParameter("error") != null) {{ %><{D} class="alert alert-error"><%= request.getParameter("error") %></{D}><% }} %>
        <form action="${{pageContext.request.contextPath}}/admin/login" method="post">
            <{D} class="form-group"><label>账号</label><input type="text" name="username" class="form-control" required></{D}>
            <{D} class="form-group"><label>密码</label><input type="password" name="password" class="form-control" required></{D}>
            <button type="submit" class="btn btn-primary" style="width:100%;padding:12px;">登录</button>
        </form>
        <{D} class="auth-links"><a href="${{pageContext.request.contextPath}}/index.jsp">返回前台</a></{D}>
    </{D}>
</{D}>
</body>
</html>
''')

write('admin/index.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<{D} class="admin-header"><h1>数据统计</h1></{D}>
<{D} class="stat-cards">
    <{D} class="stat-card"><{D} class="label">注册用户</{D}><{D} class="value"><%= userCount %></{D}></{D}>
    <{D} class="stat-card"><{D} class="label">快递总量</{D}><{D} class="value"><%= expressCount %></{D}></{D}>
    <{D} class="stat-card"><{D} class="label">快递公司</{D}><{D} class="value"><%= companyCount %></{D}></{D}>
    <{D} class="stat-card"><{D} class="label">今日查询</{D}><{D} class="value"><%= queryToday %></{D}></{D}>
</{D}>
<{D} class="admin-card">
    <h3>快递状态分布</h3>
    <table class="table">
        <tr><td>待揽收</td><td><%= pending %></td></tr>
        <tr><td>运输中</td><td><%= transit %></td></tr>
        <tr><td>派送中</td><td><%= delivering %></td></tr>
        <tr><td>已签收</td><td><%= signed %></td></tr>
    </table>
</{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

write('admin/profile.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>修改密码</h1></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<% if (request.getParameter("error") != null) {{ %><{D} class="alert alert-error"><%= request.getParameter("error") %></{D}><% }} %>
<{D} class="admin-card" style="max-width:480px;">
    <form action="${{pageContext.request.contextPath}}/admin/profile" method="post">
        <{D} class="form-group"><label>原密码</label><input type="password" name="oldPassword" class="form-control" required></{D}>
        <{D} class="form-group"><label>新密码</label><input type="password" name="newPassword" class="form-control" required></{D}>
        <button type="submit" class="btn btn-primary">保存</button>
    </form>
</{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

write('admin/logs.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<{D} class="admin-header"><h1>系统日志</h1></{D}>
<{D} class="admin-card">
    <h3>查询记录</h3>
    <table class="table">
        <thead><tr><th>单号</th><th>用户</th><th>公司</th><th>IP</th><th>时间</th></tr></thead>
        <tbody>
        <% for (QueryLog q : queryLogs) {{ %>
        <tr>
            <td><%= q.getTrackingNo() %></td>
            <td><%= q.getUsername() != null ? q.getUsername() : "游客" %></td>
            <td><%= q.getCompanyName() != null ? q.getCompanyName() : "-" %></td>
            <td><%= q.getIp() %></td>
            <td><%= sdf.format(q.getQueryTime()) %></td>
        </tr>
        <% }} %>
        </tbody>
    </table>
</{D}>
<{D} class="admin-card">
    <h3>登录记录</h3>
    <table class="table">
        <thead><tr><th>类型</th><th>账号</th><th>IP</th><th>结果</th><th>时间</th></tr></thead>
        <tbody>
        <% for (LoginLog l : loginLogs) {{ %>
        <tr>
            <td><%= l.getUserTypeText() %></td>
            <td><%= l.getUsername() %></td>
            <td><%= l.getIp() %></td>
            <td><%= l.getSuccess()==1?"成功":"失败" %></td>
            <td><%= sdf.format(l.getLoginTime()) %></td>
        </tr>
        <% }} %>
        </tbody>
    </table>
</{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

print('admin part 1 done')
