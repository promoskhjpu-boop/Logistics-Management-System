-- 快递管理系统数据库脚本
CREATE DATABASE IF NOT EXISTS logistics_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE logistics_db;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '账号',
    password VARCHAR(64) NOT NULL COMMENT '密码(MD5)',
    nickname VARCHAR(50) DEFAULT '' COMMENT '昵称',
    phone VARCHAR(20) DEFAULT '' COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '1启用 0禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 管理员表
CREATE TABLE IF NOT EXISTS admin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 快递公司表
CREATE TABLE IF NOT EXISTS company (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '公司名称',
    code VARCHAR(20) NOT NULL UNIQUE COMMENT '公司编码',
    logo VARCHAR(255) DEFAULT '' COMMENT 'Logo路径',
    phone VARCHAR(30) DEFAULT '' COMMENT '客服电话',
    website VARCHAR(255) DEFAULT '' COMMENT '官网',
    enabled TINYINT DEFAULT 1 COMMENT '1启用 0禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 快递信息表
CREATE TABLE IF NOT EXISTS express (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tracking_no VARCHAR(50) NOT NULL COMMENT '快递单号',
    user_id INT DEFAULT NULL COMMENT '绑定用户ID',
    company_id INT NOT NULL COMMENT '快递公司ID',
    receiver VARCHAR(50) DEFAULT '' COMMENT '收件人',
    sender VARCHAR(50) DEFAULT '' COMMENT '发件人',
    status TINYINT DEFAULT 0 COMMENT '0待揽收 1运输中 2派送中 3已签收',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_tracking (tracking_no),
    KEY idx_user (user_id),
    KEY idx_company (company_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 物流轨迹表
CREATE TABLE IF NOT EXISTS track (
    id INT PRIMARY KEY AUTO_INCREMENT,
    express_id INT NOT NULL COMMENT '快递ID',
    track_time DATETIME NOT NULL COMMENT '轨迹时间',
    content VARCHAR(500) NOT NULL COMMENT '轨迹内容',
    status TINYINT DEFAULT 0 COMMENT '0揽收 1运输 2中转 3派送 4签收',
    KEY idx_express (express_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 公告表
CREATE TABLE IF NOT EXISTS notice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 查询记录表
CREATE TABLE IF NOT EXISTS query_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT DEFAULT NULL,
    tracking_no VARCHAR(50) NOT NULL,
    company_id INT DEFAULT NULL,
    ip VARCHAR(50) DEFAULT '',
    query_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 登录记录表
CREATE TABLE IF NOT EXISTS login_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_type TINYINT NOT NULL COMMENT '0用户 1管理员',
    user_id INT DEFAULT NULL,
    username VARCHAR(50) DEFAULT '',
    ip VARCHAR(50) DEFAULT '',
    success TINYINT DEFAULT 1,
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 初始管理员 (密码: admin123)
INSERT INTO admin (username, password) VALUES ('admin', '0192023a7bbd73250516f069df18b500');

-- 初始用户 (密码: 123456)
INSERT INTO user (username, password, nickname, phone) VALUES
('user1', 'e10adc3949ba59abbe56e057f20f883e', '张三', '13800138001'),
('user2', 'e10adc3949ba59abbe56e057f20f883e', '李四', '13800138002');

-- 快递公司
INSERT INTO company (name, code, logo, phone, website, enabled) VALUES
('顺丰速运', 'SF', '/images/companies/sf.png', '95338', 'https://www.sf-express.com', 1),
('圆通速递', 'YTO', '/images/companies/yto.png', '95554', 'https://www.yto.net.cn', 1),
('中通快递', 'ZTO', '/images/companies/zto.png', '95311', 'https://www.zto.com', 1),
('韵达快递', 'YD', '/images/companies/yd.png', '95546', 'https://www.yundaex.com', 1),
('申通快递', 'STO', '/images/companies/sto.png', '95543', 'https://www.sto.cn', 1);

-- 示例快递
INSERT INTO express (tracking_no, user_id, company_id, receiver, sender, status) VALUES
('SF1234567890123', 1, 1, '张三', '商家A', 3),
('YTO98765432101234', 1, 2, '张三', '商家B', 1),
('ZTO55566677788899', 2, 3, '李四', '商家C', 2);

-- 示例物流轨迹
INSERT INTO track (express_id, track_time, content, status) VALUES
(1, '2026-05-20 09:00:00', '【深圳市】快件已被揽收', 0),
(1, '2026-05-20 14:30:00', '【深圳市】快件已发往广州转运中心', 1),
(1, '2026-05-21 08:00:00', '【广州市】快件到达广州转运中心', 2),
(1, '2026-05-21 16:00:00', '【广州市】快件正在派送中', 3),
(1, '2026-05-21 18:30:00', '【广州市】快件已签收，签收人：本人', 4),
(2, '2026-05-22 10:00:00', '【上海市】快件已被揽收', 0),
(2, '2026-05-22 18:00:00', '【上海市】快件已发往北京', 1),
(3, '2026-05-23 08:30:00', '【杭州市】快件已被揽收', 0),
(3, '2026-05-24 12:00:00', '【杭州市】快件运输中', 1),
(3, '2026-05-25 09:00:00', '【杭州市】快件正在派送中', 3);

-- 示例公告
INSERT INTO notice (title, content) VALUES
('系统上线公告', '快递管理系统正式上线，欢迎使用！支持多家快递公司物流查询。'),
('使用说明', '在首页输入快递单号并选择快递公司即可查询物流轨迹。注册登录后可绑定快递自动跟踪。'),
('常见问题', 'Q: 查不到物流信息？A: 请确认单号和快递公司是否正确，或联系客服。');
