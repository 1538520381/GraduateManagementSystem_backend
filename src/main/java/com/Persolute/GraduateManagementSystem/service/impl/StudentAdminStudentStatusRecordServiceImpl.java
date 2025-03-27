package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.StudentAdminStudentStatusRecordMapper;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:10
 */
@Service
public class StudentAdminStudentStatusRecordServiceImpl extends ServiceImpl<StudentAdminStudentStatusRecordMapper, StudentAdminStudentStatusRecord> implements StudentAdminStudentStatusRecordService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 根据学生id和学生管理员学生状态记录日期id获取
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午4:35
     */
    @Override
    public R getByStudentIdAndStudentAdminStudentStatusRecordDateId(Long studentId, Long studentAdminStudentStatusRecordDateId) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecord> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecord>()
                .eq(StudentAdminStudentStatusRecord::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecord::getStudentId, studentId)
                .eq(StudentAdminStudentStatusRecord::getStudentAdminStudentStatusRecordDateId, studentAdminStudentStatusRecordDateId);
        StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminStudentStatusRecord", studentAdminStudentStatusRecord);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新学生管理员学生状态记录
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午4:36
     */
    @Override
    public R update(StudentAdminStudentStatusRecord updateStudentAdminStudentStatusRecord) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecord> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecord>()
                .eq(StudentAdminStudentStatusRecord::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecord::getStudentAdminStudentStatusRecordDateId, updateStudentAdminStudentStatusRecord.getStudentAdminStudentStatusRecordDateId())
                .eq(StudentAdminStudentStatusRecord::getStudentId, updateStudentAdminStudentStatusRecord.getStudentId());

        List<StudentAdminStudentStatusRecord> studentAdminStudentStatusRecordList = super.list(lambdaQueryWrapper);

        if (!studentAdminStudentStatusRecordList.isEmpty()) {
            StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = new StudentAdminStudentStatusRecord();
            studentAdminStudentStatusRecord.setIsDeleted(true);
            super.update(studentAdminStudentStatusRecord, lambdaQueryWrapper);
        }
        this.save(updateStudentAdminStudentStatusRecord);

        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据studentId获取更新时间最新记录
     * @email 1538520381@qq.com
     * @date 2025/2/6 下午11:15
     */
    @Override
    public R getLastUpdateTimeByStudentId(Long studentId) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecord> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecord>()
                .eq(StudentAdminStudentStatusRecord::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecord::getStudentId, studentId)
                .orderByDesc(StudentAdminStudentStatusRecord::getUpdateTime)
                .last("limit 1");
        StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = super.getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminStudentStatusRecord", studentAdminStudentStatusRecord);
    }

    @Override
    public StudentAdminStudentStatusRecord getStudentAdminStudentStatusRecordByStudentIdAndStudentAdminStudentStatusRecordDateId(Long studentId, Long studentAdminStudentStatusRecordDateId) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecord> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecord>()
                .eq(StudentAdminStudentStatusRecord::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecord::getStudentId, studentId)
                .eq(StudentAdminStudentStatusRecord::getStudentAdminStudentStatusRecordDateId, studentAdminStudentStatusRecordDateId)
                .orderByDesc(StudentAdminStudentStatusRecord::getUpdateTime);
        List<StudentAdminStudentStatusRecord> studentAdminStudentStatusRecordList = list(lambdaQueryWrapper);
        return studentAdminStudentStatusRecordList.isEmpty() ? null : studentAdminStudentStatusRecordList.get(0);
    }
}
