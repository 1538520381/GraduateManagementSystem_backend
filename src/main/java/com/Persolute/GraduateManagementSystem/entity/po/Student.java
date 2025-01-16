package com.Persolute.GraduateManagementSystem.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Persolute
 * @version 1.0
 * @description 学生 PO
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:37
 */
@Data
@TableName("Student")
public class Student implements Serializable {
    private final static long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学号
    private String studentNumber;

    // 密码
    private String password;

    // 姓名
    private String name;

    // 班级号
    private String classNumber;

    // 学生类型（0：普通学生；1：一级管理员；2：二级管理员）
    private Integer type;

    // 未曾登录标识
    private Boolean hasNotLoginFlag;
}
