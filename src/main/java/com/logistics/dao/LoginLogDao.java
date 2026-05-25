package com.logistics.dao;

import com.logistics.bean.LoginLog;
import com.logistics.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginLogDao {

    public boolean add(LoginLog log) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO login_log (user_type, user_id, username, ip, success) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, log.getUserType());
            if (log.getUserId() != null) {
                ps.setInt(2, log.getUserId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, log.getUsername());
            ps.setString(4, log.getIp());
            ps.setInt(5, log.getSuccess());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public List<LoginLog> findRecent(int limit) {
        List<LoginLog> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM login_log ORDER BY login_time DESC LIMIT ?");
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    private LoginLog mapRow(ResultSet rs) throws SQLException {
        LoginLog log = new LoginLog();
        log.setId(rs.getInt("id"));
        log.setUserType(rs.getInt("user_type"));
        int userId = rs.getInt("user_id");
        if (!rs.wasNull()) log.setUserId(userId);
        log.setUsername(rs.getString("username"));
        log.setIp(rs.getString("ip"));
        log.setSuccess(rs.getInt("success"));
        log.setLoginTime(rs.getTimestamp("login_time"));
        return log;
    }
}
