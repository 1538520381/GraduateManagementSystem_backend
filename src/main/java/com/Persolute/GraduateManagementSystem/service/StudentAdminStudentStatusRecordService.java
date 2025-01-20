package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord Service
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:10
 */
public interface StudentAdminStudentStatusRecordService extends IService<StudentAdminStudentStatusRecord> {
    R getByStudentIdAndStudentAdminStudentStatusRecordDateId(Long studentId, Long studentAdminStudentStatusRecordDateId);

    R update(StudentAdminStudentStatusRecord studentAdminStudentStatusRecord);
}
