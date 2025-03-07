package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordName;
import com.Persolute.GraduateManagementSystem.mapper.StudentAdminStudentStatusRecordNameMapper;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordNameService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordName ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/03/06 15:35
 */
@Service
public class StudentAdminStudentStatusRecordNameServiceImpl extends ServiceImpl<StudentAdminStudentStatusRecordNameMapper, StudentAdminStudentStatusRecordName> implements StudentAdminStudentStatusRecordNameService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表根据studentAdminStudentStatusrRecordDateId
     * @email 1538520381@qq.com
     * @date 2025/3/6 下午3:39
     */
    @Override
    public List<StudentAdminStudentStatusRecordName> getListByStudentAdminStudentStatusRecordDateId(Long studentAdminStudentStatusrRecordDateId) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordName> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordName>()
                .eq(StudentAdminStudentStatusRecordName::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecordName::getStudentAdminStudentStatusRecordDateId, studentAdminStudentStatusrRecordDateId);
        return super.list(lambdaQueryWrapper);
    }
}
