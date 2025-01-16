package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.StudentAddListErrorVo;
import com.Persolute.GraduateManagementSystem.mapper.StudentMapper;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Student ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:42
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public R addList(List<Student> studentList) {
        List<StudentAddListErrorVo> errorList = new ArrayList<>();
        List<Student> successList = new ArrayList<>();

        for (Student student : studentList) {
            if (student.getStudentNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("学号不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getName() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("姓名不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getClassNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("班级号不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getIdNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("身份证号（后六位）不能为空");
                errorList.add(studentAddListErrorVo);
            } else {
                LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>().eq(Student::getStudentNumber, student.getStudentNumber());
                if (this.getOne(lambdaQueryWrapper) != null) {
                    StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                    BeanUtils.copyProperties(student, studentAddListErrorVo);
                    studentAddListErrorVo.setErrorMessage("该学号学生已存在");
                    errorList.add(studentAddListErrorVo);
                } else {
                    student.setPassword(student.getIdNumber());
                    student.setType(0);
                    student.setHasNotLoginFlag(true);
                    successList.add(student);
                }
            }
        }
        if (!successList.isEmpty()) {
            if (!this.saveBatch(successList)) {
                return R.error();
            }
        }

        return R.success().put("errorList", errorList);
    }
}
