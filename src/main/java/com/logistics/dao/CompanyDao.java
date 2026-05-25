package com.logistics.dao;

import com.logistics.bean.Company;
import com.logistics.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao {

    public List<Company> findAll() {
        return findList("SELECT * FROM company ORDER BY id");
    }

    public List<Company> findEnabled() {
        return findList("SELECT * FROM company WHERE enabled = 1 ORDER BY id");
    }

    private List<Company> findList(String sql) {
        List<Company> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    public Company findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM company WHERE id = ?");
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

    public Company findByCode(String code) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("SELECT * FROM company WHERE code = ?");
            ps.setString(1, code);
            rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return null;
    }

    public boolean add(Company company) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("INSERT INTO company (name, code, logo, phone, website, enabled) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, company.getName());
            ps.setString(2, company.getCode());
            ps.setString(3, company.getLogo());
            ps.setString(4, company.getPhone());
            ps.setString(5, company.getWebsite());
            ps.setInt(6, company.getEnabled());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps, conn);
        }
        return false;
    }

    public boolean update(Company company) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement("UPDATE company SET name=?, code=?, logo=?, phone=?, website=?, enabled=? WHERE id=?");
            ps.setString(1, company.getName());
            ps.setString(2, company.getCode());
            ps.setString(3, company.getLogo());
            ps.setString(4, company.getPhone());
            ps.setString(5, company.getWebsite());
            ps.setInt(6, company.getEnabled());
            ps.setInt(7, company.getId());
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
            ps = conn.prepareStatement("DELETE FROM company WHERE id = ?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
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
            ps = conn.prepareStatement("SELECT COUNT(*) FROM company");
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return 0;
    }

    private Company mapRow(ResultSet rs) throws SQLException {
        Company c = new Company();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setCode(rs.getString("code"));
        c.setLogo(rs.getString("logo"));
        c.setPhone(rs.getString("phone"));
        c.setWebsite(rs.getString("website"));
        c.setEnabled(rs.getInt("enabled"));
        c.setCreateTime(rs.getTimestamp("create_time"));
        return c;
    }
}
