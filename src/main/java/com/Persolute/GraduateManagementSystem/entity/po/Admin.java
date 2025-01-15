package com.Persolute.GraduateManagementSystem.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Persolute
 * @version 1.0
 * @description 管理员 PO
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:24
 */
@Data
public class Admin implements Serializable {
    private final static long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 账号
    private String accout;

    // 密码
    private String password;
}
