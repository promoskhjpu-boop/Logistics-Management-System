# -*- coding: utf-8 -*-
import os
D = 'd' + 'iv'
base = r'd:\Logistics-Management-System\src\main\webapp'

def w(rel, content):
    path = os.path.join(base, rel.replace('/', os.sep))
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write(content)
    print('wrote', rel)

w('admin/express/edit.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<%
    Express express = null;
    String action = "add";
    if (request.getParameter("id") != null) {{
        express = new ExpressDao().findById(Integer.parseInt(request.getParameter("id")));
        action = "edit";
    }}
    List<Company> companies = new CompanyDao().findAll();
    List<User> users = new UserDao().findAll();
%>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1><%= "edit".equals(action) ? "编辑快递" : "添加快递" %></h1></{D}>
<{D} class="admin-card" style="max-width:600px;">
    <form action="${{pageContext.request.contextPath}}/admin/express" method="post">
        <input type="hidden" name="action" value="<%= action %>">
        <% if (express != null) {{ %><input type="hidden" name="id" value="<%= express.getId() %>"><% }} %>
        <{D} class="form-group"><label>快递单号</label><input type="text" name="trackingNo" class="form-control" required value="<%= express != null ? express.getTrackingNo() : "" %>"></{D}>
        <{D} class="form-group"><label>快递公司</label><select name="companyId" class="form-control" required>
            <% for (Company c : companies) {{ %><option value="<%= c.getId() %>" <%= express!=null&&express.getCompanyId()==c.getId()?"selected":"" %>><%= c.getName() %></option><% }} %>
        </select></{D}>
        <{D} class="form-group"><label>绑定用户</label><select name="userId" class="form-control">
            <option value="">不绑定</option>
            <% for (User u : users) {{ %><option value="<%= u.getId() %>" <%= express!=null&&express.getUserId()!=null&&express.getUserId().equals(u.getId())?"selected":"" %>><%= u.getUsername() %></option><% }} %>
        </select></{D}>
        <{D} class="form-group"><label>发件人</label><input type="text" name="sender" class="form-control" value="<%= express != null && express.getSender()!=null ? express.getSender() : "" %>"></{D}>
        <{D} class="form-group"><label>收件人</label><input type="text" name="receiver" class="form-control" value="<%= express != null && express.getReceiver()!=null ? express.getReceiver() : "" %>"></{D}>
        <{D} class="form-group"><label>状态</label><select name="status" class="form-control">
            <option value="0" <%= express!=null&&express.getStatus()==0?"selected":"" %>>待揽收</option>
            <option value="1" <%= express!=null&&express.getStatus()==1?"selected":"" %>>运输中</option>
            <option value="2" <%= express!=null&&express.getStatus()==2?"selected":"" %>>派送中</option>
            <option value="3" <%= express!=null&&express.getStatus()==3?"selected":"" %>>已签收</option>
        </select></{D}>
        <button type="submit" class="btn btn-primary">保存</button>
        <a href="list.jsp" class="btn btn-secondary">返回</a>
    </form>
</{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/company/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="java.util.List" %>
<% List<Company> list = new CompanyDao().findAll(); %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>物流公司管理</h1><a href="edit.jsp" class="btn btn-primary">添加公司</a></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<{D} class="admin-card">
<table class="table"><thead><tr><th>名称</th><th>编码</th><th>电话</th><th>状态</th><th>操作</th></tr></thead><tbody>
<% for (Company c : list) {{ %>
<tr><td><%= c.getName() %></td><td><%= c.getCode() %></td><td><%= c.getPhone() %></td><td><%= c.getEnabled()==1?"启用":"禁用" %></td>
<td><a href="edit.jsp?id=<%= c.getId() %>" class="btn btn-primary btn-sm">编辑</a>
<form action="${{pageContext.request.contextPath}}/admin/company" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= c.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% }} %></tbody></table></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/company/edit.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<% Company company = null; String action = "add";
if (request.getParameter("id") != null) {{ company = new CompanyDao().findById(Integer.parseInt(request.getParameter("id"))); action = "edit"; }} %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1><%= "edit".equals(action) ? "编辑公司" : "添加公司" %></h1></{D}>
<{D} class="admin-card" style="max-width:600px;">
<form action="${{pageContext.request.contextPath}}/admin/company" method="post" enctype="multipart/form-data">
<input type="hidden" name="action" value="<%= action %>">
<% if (company != null) {{ %><input type="hidden" name="id" value="<%= company.getId() %>"><% }} %>
<input type="hidden" name="existingLogo" value="<%= company != null && company.getLogo()!=null ? company.getLogo() : "" %>">
<{D} class="form-group"><label>公司名称</label><input type="text" name="name" class="form-control" required value="<%= company != null ? company.getName() : "" %>"></{D}>
<{D} class="form-group"><label>编码</label><input type="text" name="code" class="form-control" required value="<%= company != null ? company.getCode() : "" %>"></{D}>
<{D} class="form-group"><label>客服电话</label><input type="text" name="phone" class="form-control" value="<%= company != null ? company.getPhone() : "" %>"></{D}>
<{D} class="form-group"><label>官网</label><input type="text" name="website" class="form-control" value="<%= company != null ? company.getWebsite() : "" %>"></{D}>
<{D} class="form-group"><label>Logo</label><input type="file" name="logo" class="form-control"></{D}>
<{D} class="form-group"><label>状态</label><select name="enabled" class="form-control">
<option value="1" <%= company==null||company.getEnabled()==1?"selected":"" %>>启用</option>
<option value="0" <%= company!=null&&company.getEnabled()==0?"selected":"" %>>禁用</option></select></{D}>
<button type="submit" class="btn btn-primary">保存</button> <a href="list.jsp" class="btn btn-secondary">返回</a>
</form></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/user/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<% List<User> users = new UserDao().findAll(); %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>用户管理</h1></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<{D} class="admin-card">
<table class="table"><thead><tr><th>ID</th><th>账号</th><th>昵称</th><th>手机</th><th>状态</th><th>绑定快递</th><th>操作</th></tr></thead><tbody>
<% for (User u : users) {{ List<Express> exList = new ExpressDao().findByUserId(u.getId()); %>
<tr><td><%= u.getId() %></td><td><%= u.getUsername() %></td><td><%= u.getNickname() %></td><td><%= u.getPhone() %></td>
<td><%= u.getStatus()==1?"正常":"禁用" %></td><td><%= exList.size() %> 个</td><td>
<% if (u.getStatus()==1) {{ %><form action="${{pageContext.request.contextPath}}/admin/user" method="post" style="display:inline;">
<input type="hidden" name="action" value="disable"><input type="hidden" name="id" value="<%= u.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">禁用</button></form><% }} else {{ %>
<form action="${{pageContext.request.contextPath}}/admin/user" method="post" style="display:inline;">
<input type="hidden" name="action" value="enable"><input type="hidden" name="id" value="<%= u.getId() %>">
<button type="submit" class="btn btn-success btn-sm">启用</button></form><% }} %></td></tr>
<% }} %></tbody></table></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/track/edit.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% int expressId = Integer.parseInt(request.getParameter("expressId"));
Express express = new ExpressDao().findById(expressId);
List<Track> tracks = new TrackDao().findByExpressId(expressId);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
if (express == null) {{ response.sendRedirect("../express/list.jsp"); return; }} %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>物流轨迹 - <%= express.getTrackingNo() %></h1></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<{D} class="admin-card"><h3>添加轨迹</h3>
<form action="${{pageContext.request.contextPath}}/admin/track" method="post">
<input type="hidden" name="action" value="add"><input type="hidden" name="expressId" value="<%= expressId %>">
<{D} class="form-group"><label>轨迹内容</label><input type="text" name="content" class="form-control" required></{D}>
<{D} class="form-group"><label>时间（留空自动生成）</label><input type="datetime-local" name="trackTime" class="form-control"></{D}>
<{D} class="form-group"><label>状态</label><select name="status" class="form-control">
<option value="0">揽收</option><option value="1">运输</option><option value="2">中转</option>
<option value="3">派送</option><option value="4">签收</option></select></{D}>
<button type="submit" class="btn btn-primary">添加</button> <a href="../express/list.jsp" class="btn btn-secondary">返回</a>
</form></{D}>
<{D} class="admin-card"><h3>已有轨迹</h3>
<table class="table"><thead><tr><th>时间</th><th>内容</th><th>状态</th><th>操作</th></tr></thead><tbody>
<% for (Track t : tracks) {{ %>
<tr><td><%= sdf.format(t.getTrackTime()) %></td><td><%= t.getContent() %></td><td><%= t.getStatusText() %></td>
<td><form action="${{pageContext.request.contextPath}}/admin/track" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="expressId" value="<%= expressId %>">
<input type="hidden" name="id" value="<%= t.getId() %>"><button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% }} %></tbody></table></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/notice/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% List<Notice> list = new NoticeDao().findAll(); SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>公告管理</h1><a href="edit.jsp" class="btn btn-primary">发布公告</a></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<{D} class="admin-card">
<table class="table"><thead><tr><th>标题</th><th>时间</th><th>操作</th></tr></thead><tbody>
<% for (Notice n : list) {{ %>
<tr><td><%= n.getTitle() %></td><td><%= sdf.format(n.getCreateTime()) %></td><td>
<a href="edit.jsp?id=<%= n.getId() %>" class="btn btn-primary btn-sm">编辑</a>
<form action="${{pageContext.request.contextPath}}/admin/notice" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= n.getId() %>">
<button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% }} %></tbody></table></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

w('admin/notice/edit.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<% Notice notice = null; String action = "add";
if (request.getParameter("id") != null) {{ notice = new NoticeDao().findById(Integer.parseInt(request.getParameter("id"))); action = "edit"; }} %>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1><%= "edit".equals(action) ? "编辑公告" : "发布公告" %></h1></{D}>
<{D} class="admin-card" style="max-width:700px;">
<form action="${{pageContext.request.contextPath}}/admin/notice" method="post">
<input type="hidden" name="action" value="<%= action %>">
<% if (notice != null) {{ %><input type="hidden" name="id" value="<%= notice.getId() %>"><% }} %>
<{D} class="form-group"><label>标题</label><input type="text" name="title" class="form-control" required value="<%= notice != null ? notice.getTitle() : "" %>"></{D}>
<{D} class="form-group"><label>内容</label><textarea name="content" class="form-control" rows="8" required><%= notice != null ? notice.getContent() : "" %></textarea></{D}>
<button type="submit" class="btn btn-primary">保存</button> <a href="list.jsp" class="btn btn-secondary">返回</a>
</form></{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

print('all rest done')
