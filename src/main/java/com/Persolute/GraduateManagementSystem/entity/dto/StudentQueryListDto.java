package com.Persolute.GraduateManagementSystem.entity.dto;

import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description Student QueryList Dto
 * @email 1538520381@qq.com
 * @date 2025/01/16 18:35
 */
@Data
public class StudentQueryListDto {
    // 学号
    private String studentNumber;

    // 姓名
    private String name;

    // 班级号
    private String classNumber;

    // 页号
    private Integer page;

    // 单页大小
    private Integer pageSize;
}
