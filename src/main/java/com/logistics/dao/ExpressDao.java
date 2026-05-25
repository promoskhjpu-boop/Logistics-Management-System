package com.logistics.dao;

import com.logistics.bean.Express;
import com.logistics.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpressDao {

    public Express findByTrackingNo(String trackingNo) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT e.*, c.name AS company_name, c.code AS company_code, u.username " +
                "FROM express e LEFT JOIN company c ON e.company_id = c.id " +
                "LEFT JOIN user u ON e.user_id = u.id WHERE e.tracking_no = ?");
            ps.setString(1, trackingNo);
            rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public Express findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT e.*, c.name AS company_name, c.code AS company_code, u.username " +
                "FROM express e LEFT JOIN company c ON e.company_id = c.id " +
                "LEFT JOIN user u ON e.user_id = u.id WHERE e.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public List<Express> findByUserId(int userId) {
        List<Express> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "SELECT e.*, c.name AS company_name, c.code AS company_code " +
                "FROM express e LEFT JOIN company c ON e.company_id = c.id " +
                "WHERE e.user_id = ? ORDER BY e.create_time DESC");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public List<Express> search(String trackingNo, Integer status, Integer userId) {
        List<Express> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT e.*, c.name AS company_name, c.code AS company_code, u.username " +
            "FROM express e LEFT JOIN company c ON e.company_id = c.id " +
            "LEFT JOIN user u ON e.user_id = u.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (trackingNo != null && !trackingNo.isEmpty()) {
            sql.append(" AND e.tracking_no LIKE ?");
            params.add("%" + trackingNo + "%");
        }
        if (status != null) {
            sql.append(" AND e.status = ?");
            params.add(status);
        }
        if (userId != null) {
            sql.append(" AND e.user_id = ?");
            params.add(userId);
        }
        sql.append(" ORDER BY e.create_time DESC");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public boolean add(Express express) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "INSERT INTO express (tracking_no, user_id, company_id, receiver, sender, status) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, express.getTrackingNo());
            if (express.getUserId() != null) {
                ps.setInt(2, express.getUserId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, express.getCompanyId());
            ps.setString(4, express.getReceiver());
            ps.setString(5, express.getSender());
            ps.setInt(6, express.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean update(Express express) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(
                "UPDATE express SET tracking_no=?, user_id=?, company_id=?, receiver=?, sender=?, status=? WHERE id=?");
            ps.setString(1, express.getTrackingNo());
            if (express.getUserId() != null) {
                ps.setInt(2, express.getUserId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, express.getCompanyId());
            ps.setString(4, express.getReceiver());
            ps.setString(5, express.getSender());
            ps.setInt(6, express.getStatus());
            ps.setInt(7, express.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean bindToUser(int expressId, int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE express SET user_id = ? WHERE id = ?");
            ps.setInt(1, userId);
            ps.setInt(2, expressId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean updateStatus(int id, int status) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE express SET status = ? WHERE id = ?");
            ps.setInt(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("DELETE FROM track WHERE express_id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            ps = conn.prepareStatement("DELETE FROM express WHERE id = ?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            conn.commit();
            return rows > 0;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ignored) {}
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public int count() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM express");
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    public int countByStatus(int status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM express WHERE status = ?");
            ps.setInt(1, status);
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    private Express mapRow(ResultSet rs) throws SQLException {
        Express e = new Express();
        e.setId(rs.getInt("id"));
        e.setTrackingNo(rs.getString("tracking_no"));
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) e.setUserId(userId);
        e.setCompanyId(rs.getInt("company_id"));
        e.setReceiver(rs.getString("receiver"));
        e.setSender(rs.getString("sender"));
        e.setStatus(rs.getInt("status"));
        e.setCreateTime(rs.getTimestamp("create_time"));
        try {
            e.setCompanyName(rs.getString("company_name"));
            e.setCompanyCode(rs.getString("company_code"));
            e.setUsername(rs.getString("username"));
        } catch (SQLException ignored) {}
        return e;
    }
}
