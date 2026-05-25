<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.bean.User" %>
<%@ page import="com.logistics.bean.Express" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="com.logistics.dao.ExpressDao" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath() + "/login.jsp"); return; }
    List<Express> expressList = new ExpressDao().findByUserId(user.getId());
    List<Company> companies = new CompanyDao().findEnabled();
%>
<%@ include file="../includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title">我的快递</h2>
    <% if (request.getParameter("msg") != null) { %><div class="alert alert-success"><%= request.getParameter("msg") %></div><% } %>
    <% if (request.getParameter("error") != null) { %><div class="alert alert-error"><%= request.getParameter("error") %></div><% } %>

    <div class="card">
        <h3 class="card-title">添加快递</h3>
        <form action="${pageContext.request.contextPath}/user/express" method="post" style="display:flex;gap:12px;flex-wrap:wrap;">
            <input type="hidden" name="action" value="add">
            <input type="text" name="trackingNo" class="form-control" placeholder="快递单号" required style="flex:1;min-width:200px;">
            <select name="companyId" class="form-control" required style="flex:1;min-width:150px;">
                <option value="">选择快递公司</option>
                <% for (Company c : companies) { %>
                <option value="<%= c.getId() %>"><%= c.getName() %></option>
                <% } %>
            </select>
            <button type="submit" class="btn btn-primary">添加绑定</button>
        </form>
    </div>

    <div class="card">
        <h3 class="card-title">快递列表</h3>
        <% if (expressList.isEmpty()) { %>
        <p style="color:#6b7280;">暂无绑定的快递</p>
        <% } else { %>
        <table class="table">
            <thead><tr><th>单号</th><th>快递公司</th><th>状态</th><th>操作</th></tr></thead>
            <tbody>
            <% for (Express e : expressList) {
                String badge = "badge-pending";
                if (e.getStatus()==1) badge="badge-transit";
                else if (e.getStatus()==2) badge="badge-delivering";
                else if (e.getStatus()==3) badge="badge-signed";
            %>
            <tr>
                <td><a href="${pageContext.request.contextPath}/track/query?trackingNo=<%= e.getTrackingNo() %>&companyId=<%= e.getCompanyId() %>"><%= e.getTrackingNo() %></a></td>
                <td><%= e.getCompanyName() %></td>
                <td><span class="badge <%= badge %>"><%= e.getStatusText() %></span></td>
                <td>
                    <% if (e.getStatus() != 3) { %>
                    <form action="${pageContext.request.contextPath}/user/express" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="sign"><input type="hidden" name="id" value="<%= e.getId() %>">
                        <button type="submit" class="btn btn-success btn-sm">标记签收</button>
                    </form>
                    <% } %>
                    <form action="${pageContext.request.contextPath}/user/express" method="post" style="display:inline;" onsubmit="return confirmDelete('确定移除该快递？');">
                        <input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="<%= e.getId() %>">
                        <button type="submit" class="btn btn-danger btn-sm">删除</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>

<%@ include file="../includes/footer.jsp" %>
