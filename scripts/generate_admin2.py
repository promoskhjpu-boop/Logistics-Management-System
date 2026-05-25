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

write('admin/express/list.jsp', f'''<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.ExpressDao" %>
<%@ page import="com.logistics.bean.Express" %>
<%@ page import="java.util.List" %>
<%
    String trackingNo = request.getParameter("trackingNo");
    Integer status = request.getParameter("status") != null && !request.getParameter("status").isEmpty() ? Integer.parseInt(request.getParameter("status")) : null;
    List<Express> list = new ExpressDao().search(trackingNo, status, null);
%>
<%@ include file="/admin/includes/header.jsp" %>
<{D} class="admin-header"><h1>快递管理</h1><a href="edit.jsp" class="btn btn-primary">添加快递</a></{D}>
<% if (request.getParameter("msg") != null) {{ %><{D} class="alert alert-success"><%= request.getParameter("msg") %></{D}><% }} %>
<{D} class="admin-card">
    <form class="search-bar" method="get">
        <input type="text" name="trackingNo" placeholder="快递单号" value="<%= trackingNo != null ? trackingNo : "" %>">
        <select name="status">
            <option value="">全部状态</option>
            <option value="0" <%= status!=null&&status==0?"selected":"" %>>待揽收</option>
            <option value="1" <%= status!=null&&status==1?"selected":"" %>>运输中</option>
            <option value="2" <%= status!=null&&status==2?"selected":"" %>>派送中</option>
            <option value="3" <%= status!=null&&status==3?"selected":"" %>>已签收</option>
        </select>
        <button type="submit" class="btn btn-primary">查询</button>
    </form>
    <table class="table">
        <thead><tr><th>单号</th><th>公司</th><th>用户</th><th>状态</th><th>操作</th></tr></thead>
        <tbody>
        <% for (Express e : list) {{ %>
        <tr>
            <td><%= e.getTrackingNo() %></td>
            <td><%= e.getCompanyName() %></td>
            <td><%= e.getUsername() != null ? e.getUsername() : "-" %></td>
            <td><%= e.getStatusText() %></td>
            <td>
                <a href="edit.jsp?id=<%= e.getId() %>" class="btn btn-primary btn-sm">编辑</a>
                <a href="../track/edit.jsp?expressId=<%= e.getId() %>" class="btn btn-success btn-sm">轨迹</a>
                <form action="${{pageContext.request.contextPath}}/admin/express" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
                    <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= e.getId() %>">
                    <button type="submit" class="btn btn-danger btn-sm">删除</button>
                </form>
            </td>
        </tr>
        <% }} %>
        </tbody>
    </table>
</{D}>
<%@ include file="/admin/includes/footer.jsp" %>
''')

print('done')
