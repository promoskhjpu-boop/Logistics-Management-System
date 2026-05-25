<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.*" %>
<%@ page import="com.logistics.bean.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<% int expressId = Integer.parseInt(request.getParameter("expressId"));
Express express = new ExpressDao().findById(expressId);
List<Track> tracks = new TrackDao().findByExpressId(expressId);
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
if (express == null) { response.sendRedirect("../express/list.jsp"); return; } %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1>物流轨迹 - <%= express.getTrackingNo() %></h1></div>
<% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
<div class="admin-card"><h3>添加轨迹</h3>
<form action="${pageContext.request.contextPath}/admin/track" method="post">
<input type="hidden" name="action" value="add"><input type="hidden" name="expressId" value="<%= expressId %>">
<div class="form-group"><label>轨迹内容</label><input type="text" name="content" class="form-control" required></div>
<div class="form-group"><label>时间（留空自动生成）</label><input type="datetime-local" name="trackTime" class="form-control"></div>
<div class="form-group"><label>状态</label><select name="status" class="form-control">
<option value="0">揽收</option><option value="1">运输</option><option value="2">中转</option>
<option value="3">派送</option><option value="4">签收</option></select></div>
<button type="submit" class="btn btn-primary">添加</button> <a href="../express/list.jsp" class="btn btn-secondary">返回</a>
</form></div>
<div class="admin-card"><h3>已有轨迹</h3>
<table class="table"><thead><tr><th>时间</th><th>内容</th><th>状态</th><th>操作</th></tr></thead><tbody>
<% for (Track t : tracks) { %>
<tr><td><%= sdf.format(t.getTrackTime()) %></td><td><%= t.getContent() %></td><td><%= t.getStatusText() %></td>
<td><form action="${pageContext.request.contextPath}/admin/track" method="post" style="display:inline;" onsubmit="return confirm('确定删除？');">
<input type="hidden" name="action" value="delete"><input type="hidden" name="expressId" value="<%= expressId %>">
<input type="hidden" name="id" value="<%= t.getId() %>"><button type="submit" class="btn btn-danger btn-sm">删除</button></form></td></tr>
<% } %></tbody></table></div>
<%@ include file="/admin/includes/footer.jsp" %>
