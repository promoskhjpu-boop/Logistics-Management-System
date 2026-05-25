package com.logistics.dao;

import com.logistics.bean.QueryLog;
import com.logistics.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryLogDao {

    public boolean add(QueryLog log) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO query_log (user_id, tracking_no, company_id, ip) VALUES (?, ?, ?, ?)");
            if (log.getUserId() != null) {
                ps.setInt(1, log.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setString(2, log.getTrackingNo());
            if (log.getCompanyId() != null) {
                ps.setInt(3, log.getCompanyId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, log.getIp());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public List<QueryLog> findRecent(int limit) {
        List<QueryLog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT q.*, u.username, c.name AS company_name FROM query_log q " +
                "LEFT JOIN user u ON q.user_id = u.id LEFT JOIN company c ON q.company_id = c.id " +
                "ORDER BY q.query_time DESC LIMIT ?");
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                QueryLog log = new QueryLog();
                log.setId(rs.getInt("id"));
                int userId = rs.getInt("user_id");
                if (!rs.wasNull()) log.setUserId(userId);
                log.setTrackingNo(rs.getString("tracking_no"));
                int companyId = rs.getInt("company_id");
                if (!rs.wasNull()) log.setCompanyId(companyId);
                log.setIp(rs.getString("ip"));
                log.setQueryTime(rs.getTimestamp("query_time"));
                log.setUsername(rs.getString("username"));
                log.setCompanyName(rs.getString("company_name"));
                list.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public List<QueryLog> findByUserId(int userId, int limit) {
        List<QueryLog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT q.*, c.name AS company_name FROM query_log q " +
                "LEFT JOIN company c ON q.company_id = c.id " +
                "WHERE q.user_id = ? ORDER BY q.query_time DESC LIMIT ?");
            ps.setInt(1, userId);
            ps.setInt(2, limit);
            rs = ps.executeQuery();
            while (rs.next()) {
                QueryLog log = new QueryLog();
                log.setId(rs.getInt("id"));
                log.setUserId(userId);
                log.setTrackingNo(rs.getString("tracking_no"));
                int companyId = rs.getInt("company_id");
                if (!rs.wasNull()) log.setCompanyId(companyId);
                log.setQueryTime(rs.getTimestamp("query_time"));
                log.setCompanyName(rs.getString("company_name"));
                list.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public int countToday() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM query_log WHERE DATE(query_time) = CURDATE()");
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    public int count() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM query_log");
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }
}
