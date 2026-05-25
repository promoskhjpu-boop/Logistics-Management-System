<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.bean.Express" %>
<%@ page import="com.logistics.bean.Track" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Express express = (Express) request.getAttribute("express");
    List<Track> tracks = (List<Track>) request.getAttribute("tracks");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (express == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String badgeClass = "badge-pending";
    switch (express.getStatus()) {
        case 1: badgeClass = "badge-transit"; break;
        case 2: badgeClass = "badge-delivering"; break;
        case 3: badgeClass = "badge-signed"; break;
    }
%>
<%@ include file="includes/header.jsp" %>

<div class="container section">
    <h2 class="page-title">物流详情</h2>

    <div class="card">
        <div style="display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:12px;">
            <div>
                <p><strong>快递单号：</strong><span id="trackingNo"><%= express.getTrackingNo() %></span></p>
                <p><strong>快递公司：</strong><%= express.getCompanyName() %></p>
                <p><strong>发件人：</strong><%= express.getSender() != null ? express.getSender() : "-" %></p>
                <p><strong>收件人：</strong><%= express.getReceiver() != null ? express.getReceiver() : "-" %></p>
            </div>
            <div>
                <span class="badge <%= badgeClass %>"><%= express.getStatusText() %></span>
            </div>
        </div>
        <div style="margin-top:16px;">
            <button class="btn btn-primary btn-sm" onclick="copyText('<%= express.getTrackingNo() %>')">复制单号</button>
            <a href="${pageContext.request.contextPath}/track/query?trackingNo=<%= express.getTrackingNo() %>&companyId=<%= express.getCompanyId() %>"
               class="btn btn-success btn-sm">刷新查询</a>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary btn-sm">返回首页</a>
        </div>
    </div>

    <div class="card">
        <h3 class="card-title">物流轨迹</h3>
        <% if (tracks == null || tracks.isEmpty()) { %>
        <p style="color:#6b7280;">暂无物流信息</p>
        <% } else { %>
        <div class="timeline">
            <% for (Track t : tracks) { %>
            <div class="timeline-item">
                <div class="timeline-time"><%= sdf.format(t.getTrackTime()) %> · <%= t.getStatusText() %></div>
                <div class="timeline-content"><%= t.getContent() %></div>
            </div>
            <% } %>
        </div>
        <% } %>
    </div>
</div>

<script>
    saveQueryHistory('<%= express.getTrackingNo() %>', '<%= express.getCompanyName() %>');
</script>

<%@ include file="includes/footer.jsp" %>
