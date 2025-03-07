package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordName;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordName Service
 * @email 1538520381@qq.com
 * @date 2025/03/06 15:34
 */
public interface StudentAdminStudentStatusRecordNameService extends IService<StudentAdminStudentStatusRecordName> {
    List<StudentAdminStudentStatusRecordName> getListByStudentAdminStudentStatusRecordDateId(Long studentAdminStudentStatusrRecordDateId);
}
