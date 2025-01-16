package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.StudentAddListErrorVo;
import com.Persolute.GraduateManagementSystem.mapper.StudentMapper;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生列表
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
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
                if (super.getOne(lambdaQueryWrapper) != null) {
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
            if (!super.saveBatch(successList)) {
                return R.error();
            }
        }

        return R.success().put("errorList", errorList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生分页
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @Override
    public R queryPage(Student student, Integer page, Integer pageSize) {
        Page<Student> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (student.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, student.getStudentNumber());
        }

        if (student.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, student.getName());
        }

        if (student.getClassNumber() != null) {
            lambdaQueryWrapper.like(Student::getClassNumber, student.getClassNumber());
        }

        super.page(pageInfo, lambdaQueryWrapper);

        return R.success().put("pageInfo", pageInfo);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:29
     */
    @Override
    public R deleteById(Long id) {
        if (!super.removeById(id)) {
            return R.error();
        }
        return R.success();
    }
}
