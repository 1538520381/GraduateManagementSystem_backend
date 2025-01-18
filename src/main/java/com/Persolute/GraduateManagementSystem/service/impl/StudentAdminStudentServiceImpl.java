package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
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
        LambdaQueryWrapper<StudentAdminStudent> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudent>()
                .eq(StudentAdminStudent::getIsDeleted, false)
                .eq(StudentAdminStudent::getStudentAdminId, studentAdminId);
        StudentAdminStudent studentAdminStudent = new StudentAdminStudent();
        studentAdminStudent.setIsDeleted(true);
        super.update(studentAdminStudent, lambdaQueryWrapper);
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
        LambdaQueryWrapper<StudentAdminStudent> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudent>()
                .eq(StudentAdminStudent::getIsDeleted, false)
                .eq(StudentAdminStudent::getStudentId, studentId);
        StudentAdminStudent studentAdminStudent = super.getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminId", studentAdminStudent == null ? null : studentAdminStudent.getStudentAdminId());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生管理员学生对照
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午6:36
     */
    @Override
    public R addStudentAdminStudent(StudentAdminStudent studentAdminStudent) {
        LambdaQueryWrapper<StudentAdminStudent> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudent>()
                .eq(StudentAdminStudent::getIsDeleted, false)
                .eq(StudentAdminStudent::getStudentId, studentAdminStudent.getStudentId());
        if (super.getOne(lambdaQueryWrapper) != null) {
            throw new CustomerException("该学生已有组");
        }

        if (!super.save(studentAdminStudent)) {
            throw new CustomerException("服务器异常");
        }
        return R.success();
    }
}
