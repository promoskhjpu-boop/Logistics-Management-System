# -*- coding: utf-8 -*-
import os

D = 'd' + 'iv'

def tag(name, cls=None, content=''):
    if cls:
        return f'<{name} class="{cls}">{content}</{name}>'
    return f'<{name}>{content}</{name}>'

base = r'd:\Logistics-Management-System\src\main\webapp'

def write(rel, content):
    path = os.path.join(base, rel.replace('/', os.sep))
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print('wrote', rel)

write('user/profile.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.bean.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {{ response.sendRedirect(request.getContextPath() + "/login.jsp"); return; }}
%>
<%@ include file="../includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title">个人中心</h2>
    <% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
    <% if (request.getParameter("error") != null) {{ %><{D} class="alert alert-error"><%= request.getParameter("error") %></{D}><% }} %>
    <{D} class="grid grid-2">
        <{D} class="card">
            <h3 class="card-title">基本信息</h3>
            <form action="${{pageContext.request.contextPath}}/user/profile" method="post">
                <{D} class="form-group"><label>账号</label><input type="text" class="form-control" value="<%= user.getUsername() %>" disabled></{D}>
                <{D} class="form-group"><label>昵称</label><input type="text" name="nickname" class="form-control" value="<%= user.getNickname() %>"></{D}>
                <{D} class="form-group"><label>手机号</label><input type="text" name="phone" class="form-control" value="<%= user.getPhone() %>"></{D}>
                <button type="submit" class="btn btn-primary">保存修改</button>
            </form>
        </{D}>
        <{D} class="card">
            <h3 class="card-title">修改密码</h3>
            <form action="${{pageContext.request.contextPath}}/user/profile" method="post">
                <input type="hidden" name="action" value="password">
                <{D} class="form-group"><label>原密码</label><input type="password" name="oldPassword" class="form-control" required></{D}>
                <{D} class="form-group"><label>新密码</label><input type="password" name="newPassword" class="form-control" required></{D}>
                <button type="submit" class="btn btn-primary">修改密码</button>
            </form>
        </{D}>
    </{D}>
</{D}>

<%@ include file="../includes/footer.jsp" %>
''')

write('user/my-express.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.bean.User" %>
<%@ page import="com.logistics.bean.Express" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="com.logistics.dao.ExpressDao" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {{ response.sendRedirect(request.getContextPath() + "/login.jsp"); return; }}
    List<Express> expressList = new ExpressDao().findByUserId(user.getId());
    List<Company> companies = new CompanyDao().findEnabled();
%>
<%@ include file="../includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title">我的快递</h2>
    <% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
    <% if (request.getParameter("error") != null) {{ %><{D} class="alert alert-error"><%= request.getParameter("error") %></{D}><% }} %>

    <{D} class="card">
        <h3 class="card-title">添加快递</h3>
        <form action="${{pageContext.request.contextPath}}/user/express" method="post" style="display:flex;gap:12px;flex-wrap:wrap;">
            <input type="hidden" name="action" value="add">
            <input type="text" name="trackingNo" class="form-control" placeholder="快递单号" required style="flex:1;min-width:200px;">
            <select name="companyId" class="form-control" required style="flex:1;min-width:150px;">
                <option value="">选择快递公司</option>
                <% for (Company c : companies) {{ %>
                <option value="<%= c.getId() %>"><%= c.getName() %></option>
                <% }} %>
            </select>
            <button type="submit" class="btn btn-primary">添加绑定</button>
        </form>
    </{D}>

    <{D} class="card">
        <h3 class="card-title">快递列表</h3>
        <% if (expressList.isEmpty()) {{ %>
        <p style="color:#6b7280;">暂无绑定的快递</p>
        <% }} else {{ %>
        <table class="table">
            <thead><tr><th>单号</th><th>快递公司</th><th>状态</th><th>操作</th></tr></thead>
            <tbody>
            <% for (Express e : expressList) {{
                String badge = "badge-pending";
                if (e.getStatus()==1) badge="badge-transit";
                else if (e.getStatus()==2) badge="badge-delivering";
                else if (e.getStatus()==3) badge="badge-signed";
            %>
            <tr>
                <td><a href="${{pageContext.request.contextPath}}/track/query?trackingNo=<%= e.getTrackingNo() %>&companyId=<%= e.getCompanyId() %>"><%= e.getTrackingNo() %></a></td>
                <td><%= e.getCompanyName() %></td>
                <td><span class="badge <%= badge %>"><%= e.getStatusText() %></span></td>
                <td>
                    <% if (e.getStatus() != 3) {{ %>
                    <form action="${{pageContext.request.contextPath}}/user/express" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="sign"><input type="hidden" name="id" value="<%= e.getId() %>">
                        <button type="submit" class="btn btn-success btn-sm">标记签收</button>
                    </form>
                    <% }} %>
                    <form action="${{pageContext.request.contextPath}}/user/express" method="post" style="display:inline;" onsubmit="return confirmDelete('确定移除该快递？');">
                        <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= e.getId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">删除</button>
                    </form>
                </td>
            </tr>
            <% }} %>
            </tbody>
        </table>
        <% }} %>
    </{D}>
</{D}>

<%@ include file="../includes/footer.jsp" %>
''')

print('done part 1')

write('company/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="java.util.List" %>
<% List<Company> companies = new CompanyDao().findEnabled(); %>
<%@ include file="../includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title">快递公司</h2>
    <{D} class="grid grid-3">
        <% for (Company c : companies) {{ %>
        <{D} class="card company-card">
            <{D} class="company-logo"><%= c.getCode() %></{D}>
            <{D} class="company-name"><%= c.getName() %></{D}>
            <p class="company-phone">客服电话：<%= c.getPhone() %></p>
            <% if (c.getWebsite() != null && !c.getWebsite().isEmpty()) {{ %>
            <p><a href="<%= c.getWebsite() %>" target="_blank">访问官网</a></p>
            <% }} %>
        </{D}>
        <% }} %>
    </{D}>
</{D}>

<%@ include file="../includes/footer.jsp" %>
''')

write('notice/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% List<Notice> notices = new NoticeDao().findAll(); SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); %>
<%@ include file="../includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title">系统公告</h2>
    <{D} class="card">
        <% if (notices.isEmpty()) {{ %><p>暂无公告</p><% }} else {{ %>
        <table class="table">
            <thead><tr><th>标题</th><th>发布时间</th><th>操作</th></tr></thead>
            <tbody>
            <% for (Notice n : notices) {{ %>
            <tr>
                <td><%= n.getTitle() %></td>
                <td><%= sdf.format(n.getCreateTime()) %></td>
                <td><a href="${{pageContext.request.contextPath}}/notice/detail.jsp?id=<%= n.getId() %>" class="btn btn-primary btn-sm">查看</a></td>
            </tr>
            <% }} %>
            </tbody>
        </table>
        <% }} %>
    </{D}>
</{D}>

<%@ include file="../includes/footer.jsp" %>
''')

write('notice/detail.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Notice notice = new NoticeDao().findById(id);
    if (notice == null) {{ response.sendRedirect(request.getContextPath() + "/notice/list.jsp"); return; }}
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<%@ include file="../includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title"><%= notice.getTitle() %></h2>
    <{D} class="card">
        <p style="color:#6b7280;margin-bottom:16px;">发布时间：<%= sdf.format(notice.getCreateTime()) %></p>
        <{D} style="line-height:1.8;white-space:pre-wrap;"><%= notice.getContent() %></{D}>
        <p style="margin-top:20px;"><a href="${{pageContext.request.contextPath}}/notice/list.jsp" class="btn btn-secondary">返回列表</a></p>
    </{D}>
</{D}>

<%@ include file="../includes/footer.jsp" %>
''')

write('help.jsp', f'''<%@ include file="includes/header.jsp" %>

<{D} class="container section">
    <h2 class="page-title">帮助中心</h2>
    <{D} class="grid grid-2">
        <{D} class="card">
            <h3 class="card-title">使用说明</h3>
            <p style="line-height:2;font-size:14px;color:#374151;">
                1. 在首页输入快递单号，选择对应快递公司，点击查询<br>
                2. 注册并登录后，可在「我的快递」中绑定单号自动跟踪<br>
                3. 支持复制单号、刷新查询、查看历史记录<br>
                4. 管理员可在后台维护快递信息与物流轨迹
            </p>
        </{D}>
        <{D} class="card">
            <h3 class="card-title">常见问题 FAQ</h3>
            <p style="line-height:2;font-size:14px;color:#374151;">
                <strong>Q: 查不到物流信息？</strong><br>A: 请确认单号和快递公司是否正确。<br><br>
                <strong>Q: 如何绑定快递？</strong><br>A: 登录后在「我的快递」页面添加单号即可。<br><br>
                <strong>Q: 忘记密码怎么办？</strong><br>A: 在登录页点击「忘记密码」，输入账号和注册手机号重置。
            </p>
        </{D}>
    </{D}>
</{D}>

<%@ include file="includes/footer.jsp" %>
''')

print('done part 2')
