package com.Persolute.GraduateManagementSystem.entity.vo.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description Student WithStudentAdmin VO
 * @email 1538520381@qq.com
 * @date 2025/01/17 16:10
 */
@Data
public class WithStudentAdminVO extends Student {
    // 学生管理员
    private Student studentAdmin;
}
