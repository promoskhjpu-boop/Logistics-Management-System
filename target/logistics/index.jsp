<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.logistics.dao.CompanyDao" %>
<%@ page import="com.logistics.bean.Company" %>
<%@ page import="java.util.List" %>
<%
    List<Company> companies = new CompanyDao().findEnabled();
    request.setAttribute("pageTitle", "首页");
%>
<%@ include file="includes/header.jsp" %>

<main class="home">
    <!-- 主视觉区 -->
    <section class="home-hero">
        <div class="home-hero-bg"></div>
        <div class="container home-hero-inner">
            <div class="home-hero-text">
                <span class="home-badge">全国物流 · 一键追踪</span>
                <h1>随时随地，查快递</h1>
                <p>输入单号即可查看完整物流轨迹，支持多家主流快递公司，安全、快速、准确。</p>
            </div>

            <div class="home-search-card">
                <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-error"><%= request.getParameter("error") %></div>
                <% } %>
                <form class="home-search-form search-form" action="<%= request.getContextPath() %>/track/query" method="get">
                    <div class="home-search-row">
                        <label class="home-field">
                            <span class="home-field-label">快递单号</span>
                            <input type="text" name="trackingNo" placeholder="请输入快递单号" required
                                   value="<%= request.getParameter("trackingNo") != null ? request.getParameter("trackingNo") : "" %>">
                        </label>
                        <label class="home-field">
                            <span class="home-field-label">快递公司</span>
                            <select name="companyId" required>
                                <option value="">请选择快递公司</option>
                                <% for (Company c : companies) { %>
                                <option value="<%= c.getId() %>"><%= c.getName() %></option>
                                <% } %>
                            </select>
                        </label>
                    </div>
                    <button type="submit" class="home-search-btn">
                        <span>立即查询</span>
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
                    </button>
                </form>
                <p class="home-search-tip">示例单号：SF1234567890123 · 选择「顺丰速运」即可体验</p>
            </div>
        </div>
    </section>

    <!-- 数据概览 -->
    <section class="home-stats">
        <div class="container home-stats-grid">
            <div class="home-stat-item">
                <strong><%= companies.size() %>+</strong>
                <span>合作快递品牌</span>
            </div>
            <div class="home-stat-item">
                <strong>24h</strong>
                <span>全天候查询服务</span>
            </div>
            <div class="home-stat-item">
                <strong>实时</strong>
                <span>物流轨迹更新</span>
            </div>
            <div class="home-stat-item">
                <strong>免费</strong>
                <span>注册即可绑定快递</span>
            </div>
        </div>
    </section>

    <!-- 功能特色 -->
    <section class="home-section">
        <div class="container">
            <div class="home-section-head">
                <h2>为什么选择我们</h2>
                <p>简洁高效的快递查询与管理体验</p>
            </div>
            <div class="home-features">
                <article class="home-feature-card">
                    <div class="home-feature-icon home-icon-blue">📦</div>
                    <h3>单号查询</h3>
                    <p>输入单号 + 选择快递公司，秒级返回完整物流轨迹，支持复制与刷新。</p>
                </article>
                <article class="home-feature-card">
                    <div class="home-feature-icon home-icon-orange">🔔</div>
                    <h3>自动跟踪</h3>
                    <p>登录后绑定快递单号，待揽收、运输中、派送中、已签收状态一目了然。</p>
                </article>
                <article class="home-feature-card">
                    <div class="home-feature-icon home-icon-green">🛡️</div>
                    <h3>安全可靠</h3>
                    <p>账号密码加密存储，查询记录可追溯，个人信息严格保护。</p>
                </article>
            </div>
        </div>
    </section>

    <!-- 历史 & 说明 -->
    <section class="home-section home-section-gray">
        <div class="container home-two-col">
            <div class="home-panel">
                <div class="home-panel-head">
                    <h3>查询历史</h3>
                    <span class="home-panel-tag">本地记录</span>
                </div>
                <div id="queryHistory" class="home-history"></div>
            </div>
            <div class="home-panel">
                <div class="home-panel-head">
                    <h3>使用指南</h3>
                </div>
                <ol class="home-steps">
                    <li><span>1</span>在上方输入快递单号，选择对应快递公司</li>
                    <li><span>2</span>点击「立即查询」，查看详细物流轨迹</li>
                    <li><span>3</span>注册登录后，可在「我的快递」绑定单号自动跟踪</li>
                    <li><span>4</span>支持复制单号、刷新查询、查看历史记录</li>
                </ol>
            </div>
        </div>
    </section>

    <!-- 快递公司 -->
    <section class="home-section">
        <div class="container">
            <div class="home-section-head">
                <h2>支持的快递公司</h2>
                <p>覆盖国内主流快递品牌，持续扩展中</p>
                <a href="<%= request.getContextPath() %>/company/list.jsp" class="home-link-more">查看全部 →</a>
            </div>
            <div class="home-company-grid">
                <% for (Company c : companies) { %>
                <a href="<%= request.getContextPath() %>/company/list.jsp" class="home-company-card">
                    <div class="home-company-logo"><%= c.getCode() %></div>
                    <h4><%= c.getName() %></h4>
                    <p><%= c.getPhone() %></p>
                </a>
                <% } %>
            </div>
        </div>
    </section>
</main>

<script>
    renderQueryHistory('queryHistory', '<%= request.getContextPath() %>');
    document.querySelector('.home-search-form').addEventListener('submit', function() {
        var no = document.querySelector('[name=trackingNo]').value;
        var sel = document.querySelector('[name=companyId]');
        var name = sel.options[sel.selectedIndex].text;
        var companyId = sel.value;
        saveQueryHistory(no, name, companyId);
    });
</script>

<%@ include file="includes/footer.jsp" %>
