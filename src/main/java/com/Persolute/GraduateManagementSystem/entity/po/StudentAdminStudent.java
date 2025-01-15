package com.Persolute.GraduateManagementSystem.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Persolute
 * @version 1.0
 * @description 学生管理员_学生对照 PO
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:45
 */
@Data
public class StudentAdminStudent implements Serializable {
    private final static long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学生管理员主键
    private Long studentAdminId;

    // 学生主键
    private Long studentId;
}
