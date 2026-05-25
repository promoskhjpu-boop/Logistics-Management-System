package com.logistics.dao;

import com.logistics.bean.Track;
import com.logistics.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDao {

    public List<Track> findByExpressId(int expressId) {
        List<Track> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM track WHERE express_id = ? ORDER BY track_time DESC");
            ps.setInt(1, expressId);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public Track findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM track WHERE id = ?");
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

    public boolean add(Track track) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO track (express_id, track_time, content, status) VALUES (?, ?, ?, ?)");
            ps.setInt(1, track.getExpressId());
            ps.setTimestamp(2, new Timestamp(track.getTrackTime().getTime()));
            ps.setString(3, track.getContent());
            ps.setInt(4, track.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean update(Track track) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE track SET track_time=?, content=?, status=? WHERE id=?");
            ps.setTimestamp(1, new Timestamp(track.getTrackTime().getTime()));
            ps.setString(2, track.getContent());
            ps.setInt(3, track.getStatus());
            ps.setInt(4, track.getId());
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
            ps = conn.prepareStatement("DELETE FROM track WHERE id = ?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean deleteByExpressId(int expressId) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("DELETE FROM track WHERE express_id = ?");
            ps.setInt(1, expressId);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    private Track mapRow(ResultSet rs) throws SQLException {
        Track t = new Track();
        t.setId(rs.getInt("id"));
        t.setExpressId(rs.getInt("express_id"));
        t.setTrackTime(rs.getTimestamp("track_time"));
        t.setContent(rs.getString("content"));
        t.setStatus(rs.getInt("status"));
        return t;
    }
}
