package com.Persolute.GraduateManagementSystem.entity.dto.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Student AddList Dto
 * @email 1538520381@qq.com
 * @date 2025/01/16 18:16
 */
@Data
public class AddListDto {
    // 学生列表
    private List<Student> studentList;
}
