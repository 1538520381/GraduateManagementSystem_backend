package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.StudentAdminStudentStatusRecordMapper;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:10
 */
@Service
public class StudentAdminStudentStatusRecordServiceImpl extends ServiceImpl<StudentAdminStudentStatusRecordMapper, StudentAdminStudentStatusRecord> implements StudentAdminStudentStatusRecordService {
    @Override
    public R getByStudentIdAndStudentAdminStudentStatusRecordDateId(Long studentId, Long studentAdminStudentStatusRecordDateId) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecord> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecord>()
                .eq(StudentAdminStudentStatusRecord::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecord::getStudentId, studentId)
                .eq(StudentAdminStudentStatusRecord::getStudentAdminStudentStatusRecordDateId, studentAdminStudentStatusRecordDateId);
        StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminStudentStatusRecord", studentAdminStudentStatusRecord);
    }
}
