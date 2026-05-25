<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.NoticeDao" %>
<%@ page import="com.logistics.bean.Notice" %>
<% Notice notice = null; String action = "add";
if (request.getParameter("id") != null) { notice = new NoticeDao().findById(Integer.parseInt(request.getParameter("id"))); action = "edit"; } %>
<%@ include file="/admin/includes/header.jsp" %>
<div class="admin-header"><h1><%= "edit".equals(action) ? "编辑公告" : "发布公告" %></h1></div>
<div class="admin-card" style="max-width:700px;">
<form action="${pageContext.request.contextPath}/admin/notice" method="post">
<input type="hidden" name="action" value="<%= action %>">
<% if (notice != null) { %><input type="hidden" name="id" value="<%= notice.getId() %>"><% } %>
<div class="form-group"><label>标题</label><input type="text" name="title" class="form-control" required value="<%= notice != null ? notice.getTitle() : "" %>"></div>
<div class="form-group"><label>内容</label><textarea name="content" class="form-control" rows="8" required><%= notice != null ? notice.getContent() : "" %></textarea></div>
<button type="submit" class="btn btn-primary">保存</button> <a href="list.jsp" class="btn btn-secondary">返回</a>
</form></div>
<%@ include file="/admin/includes/footer.jsp" %>
