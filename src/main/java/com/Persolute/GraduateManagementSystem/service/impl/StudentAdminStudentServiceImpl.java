package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.StudentAdminStudentMapper;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudent ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:49
 */
@Service
public class StudentAdminStudentServiceImpl extends ServiceImpl<StudentAdminStudentMapper, StudentAdminStudent> implements StudentAdminStudentService {

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据学生管理员id移除
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午10:08
     */
    @Override
    public R deleteByStudentAdminId(Long studentAdminId) {
        LambdaQueryWrapper<StudentAdminStudent> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudent>().eq(StudentAdminStudent::getStudentAdminId, studentAdminId);
        super.remove(lambdaQueryWrapper);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据学生id获取对应管理员id
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午4:35
     */
    @Override
    public R getByStudentId(Long studentId) {
        LambdaQueryWrapper<StudentAdminStudent> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudent>().eq(StudentAdminStudent::getStudentId, studentId);
        StudentAdminStudent studentAdminStudent = super.getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminId", studentAdminStudent == null ? null : studentAdminStudent.getStudentAdminId());
    }
}
