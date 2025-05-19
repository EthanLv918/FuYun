-- 使用utf8mb4字符集支持emoji和中文
CREATE DATABASE IF NOT EXISTS stock_app DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE stock_app;

-- 用户表（核心基础表）
CREATE TABLE users (
    user_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增用户ID',
    openid VARCHAR(28) NOT NULL UNIQUE COMMENT '微信OpenID',
    nickname VARCHAR(50) NOT NULL COMMENT '用户昵称',
    avatar_url VARCHAR(255) COMMENT '头像URL',
    is_competitor TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否参赛者(0-否 1-是)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_openid (openid)
) COMMENT='用户基本信息表';

-- 用户统计表（数据分析）
CREATE TABLE user_stats (
    stat_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    total_assets DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '总资产',
    total_profit_ratio DECIMAL(7,4) NOT NULL DEFAULT 0.0000 COMMENT '总收益率%',
    yesterday_profit_ratio DECIMAL(7,4) NOT NULL DEFAULT 0.0000 COMMENT '昨日收益率%',
    yesterday_rank INT COMMENT '昨日排名',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user (user_id)
) COMMENT='用户统计数据表';

-- 比赛主表（支持多届比赛）
CREATE TABLE competitions (
    competition_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT '比赛名称',
    season_num INT UNSIGNED NOT NULL COMMENT '届数',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME NOT NULL COMMENT '结束时间',
    status TINYINT(1) NOT NULL DEFAULT 0 COMMENT '状态(0-未开始 1-进行中 2-已结束)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_season (season_num)
) COMMENT='比赛主信息表';

-- 参赛者分组表（灵活分组配置）
CREATE TABLE competition_groups (
    group_id TINYINT UNSIGNED PRIMARY KEY COMMENT '组别ID',
    group_name VARCHAR(20) NOT NULL COMMENT '组别名称',
    min_assets DECIMAL(15,2) COMMENT '最低资产要求',
    max_assets DECIMAL(15,2) COMMENT '最高资产要求',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) COMMENT='比赛分组配置表';

-- 用户参赛表（比赛核心表）
CREATE TABLE user_competitions (
    uc_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    competition_id INT UNSIGNED NOT NULL,
    group_id TINYINT UNSIGNED NOT NULL COMMENT '所属组别',
    initial_assets DECIMAL(15,2) NOT NULL COMMENT '初始资产',
    current_assets DECIMAL(15,2) NOT NULL COMMENT '当前资产',
    total_profit_ratio DECIMAL(7,4) NOT NULL DEFAULT 0.0000 COMMENT '总收益率',
    is_approved TINYINT(1) NOT NULL DEFAULT 0 COMMENT '审核状态(0-待审 1-通过)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (competition_id) REFERENCES competitions(competition_id),
    FOREIGN KEY (group_id) REFERENCES competition_groups(group_id),
    UNIQUE INDEX idx_user_comp (user_id, competition_id)
) COMMENT='用户参赛信息表';

-- 报单记录表（核心业务表）
CREATE TABLE trade_records (
    record_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    competition_id INT UNSIGNED NOT NULL,
    trade_date DATE NOT NULL COMMENT '交易日期',
    total_assets DECIMAL(15,2) NOT NULL COMMENT '总资产',
    stocks JSON COMMENT '持仓股票(JSON格式)',
    screenshot_url VARCHAR(255) NOT NULL COMMENT '截图URL',
    reflection TEXT COMMENT '操盘反思',
    submission_time DATETIME NOT NULL COMMENT '提交时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (competition_id) REFERENCES competitions(competition_id),
    INDEX idx_date_user (trade_date, user_id),
    INDEX idx_competition (competition_id)
) COMMENT='用户报单记录表';

-- 投资思考表（广场内容）
CREATE TABLE investment_thoughts (
    thought_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    content TEXT NOT NULL COMMENT '思考内容',
    profit DECIMAL(7,4) NOT NULL DEFAULT 0.0000 COMMENT '关联收益',
    view_count INT UNSIGNED NOT NULL DEFAULT 0,
    like_count INT UNSIGNED NOT NULL DEFAULT 0,
    comment_count INT UNSIGNED NOT NULL DEFAULT 0,
    share_count INT UNSIGNED NOT NULL DEFAULT 0,
    is_hot TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否大赚标记',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_hot (is_hot, created_at),
    INDEX idx_profit (profit DESC, created_at)
) COMMENT='投资思考广场表';

-- 用户互动表（关注关系）
CREATE TABLE user_relations (
    relation_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    follower_id INT UNSIGNED NOT NULL COMMENT '关注者',
    followed_id INT UNSIGNED NOT NULL COMMENT '被关注者',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (follower_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (followed_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE INDEX idx_relation (follower_id, followed_id)
) COMMENT='用户关注关系表';

-- 评论表（互动功能）
CREATE TABLE comments (
    comment_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    thought_id BIGINT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    content VARCHAR(500) NOT NULL,
    parent_id BIGINT UNSIGNED DEFAULT NULL COMMENT '父评论ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (thought_id) REFERENCES investment_thoughts(thought_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_thought (thought_id)
) COMMENT='评论表';

-- 点赞表（互动功能）
CREATE TABLE likes (
    like_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    thought_id BIGINT UNSIGNED NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (thought_id) REFERENCES investment_thoughts(thought_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    UNIQUE INDEX idx_unique_like (thought_id, user_id)
) COMMENT='点赞记录表';

-- 热股统计表（数据分析）
CREATE TABLE hot_stocks (
    stock_id VARCHAR(10) NOT NULL COMMENT '股票代码',
    competition_id INT UNSIGNED NOT NULL,
    hold_count INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '持仓人数',
    date DATE NOT NULL COMMENT '统计日期',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (stock_id, competition_id, date),
    FOREIGN KEY (competition_id) REFERENCES competitions(competition_id)
) COMMENT='热股统计表';

-- 收益排行榜表（定时任务更新）
CREATE TABLE profit_rankings (
    ranking_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    competition_id INT UNSIGNED NOT NULL,
    ranking_type ENUM('daily_profit','daily_loss','total_profit','total_loss') NOT NULL,
    user_id INT UNSIGNED NOT NULL,
    profit_ratio DECIMAL(7,4) NOT NULL,
    rank_position INT UNSIGNED NOT NULL,
    calculated_at DATETIME NOT NULL COMMENT '统计时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (competition_id) REFERENCES competitions(competition_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_ranking_type (competition_id, ranking_type, calculated_at)
) COMMENT='收益排行榜表';