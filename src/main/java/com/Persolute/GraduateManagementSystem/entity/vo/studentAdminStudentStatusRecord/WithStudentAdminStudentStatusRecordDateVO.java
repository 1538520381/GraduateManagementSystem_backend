package com.Persolute.GraduateManagementSystem.entity.vo.studentAdminStudentStatusRecord;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord WithStudentAdminStudentStatusRecordDate VO
 * @email 1538520381@qq.com
 * @date 2025/01/20 18:11
 */
@Data
public class WithStudentAdminStudentStatusRecordDateVO extends StudentAdminStudentStatusRecord {
    // 学生管理员学生状态记录日期
    private StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate;
}
