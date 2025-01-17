package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudent Service
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:48
 */
public interface StudentAdminStudentService extends IService<StudentAdminStudent> {
    R deleteByStudentAdminId(Long studentAdminId);

    R getByStudentId(Long studentId);
}
