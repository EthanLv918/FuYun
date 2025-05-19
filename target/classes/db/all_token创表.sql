-- 凭证存储表
CREATE TABLE all_token (
                           token_id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                           app_name VARCHAR(50) NOT NULL COMMENT '应用名称(如wechat/mini_program)',
                           token_type VARCHAR(20) NOT NULL COMMENT '凭证类型(access_token/jsapi_ticket等)',
                           token_value VARCHAR(512) NOT NULL COMMENT '凭证内容',
                           expires_in INT NOT NULL COMMENT '有效期(秒)',
                           expire_time DATETIME NOT NULL COMMENT '过期时间',
                           created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',  -- 新增这行
                           reserved1 VARCHAR(255) COMMENT '预留字段1',
                           reserved2 VARCHAR(255) COMMENT '预留字段2',
                           reserved3 VARCHAR(255) COMMENT '预留字段3',
                           reserved4 VARCHAR(255) COMMENT '预留字段4',
                           reserved5 VARCHAR(255) COMMENT '预留字段5',
                           INDEX idx_app_token (app_name, token_type),
                           INDEX idx_expire_time (expire_time)
) COMMENT='全局凭证存储表';