package com.Persolute.GraduateManagementSystem.entity.dto;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description Student QueryList Dto
 * @email 1538520381@qq.com
 * @date 2025/01/16 18:35
 */
@Data
public class StudentQueryPageDto extends Student {
    // 页号
    private Integer page;

    // 单页大小
    private Integer pageSize;
}
