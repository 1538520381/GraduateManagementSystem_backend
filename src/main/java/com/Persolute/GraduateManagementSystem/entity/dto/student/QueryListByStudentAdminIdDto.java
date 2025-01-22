package com.Persolute.GraduateManagementSystem.entity.dto.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description Student QueryListByStudentAdminId Dto
 * @email 1538520381@qq.com
 * @date 2025/01/18 14:07
 */
@Data
public class QueryListByStudentAdminIdDto extends Student {
    // 学生管理员id
    private Long studentAdminId;
}
