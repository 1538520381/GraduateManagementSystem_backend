package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.dto.student.QueryPageWithStudentAdminStudentStatusRecordDto;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Student Service
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:41
 */
public interface StudentService extends IService<Student> {
    R addList(List<Student> studentList);

    R queryPage(Student student, Integer page, Integer pageSize);

    R deleteById(Long id);

    R deleteByIds(List<Long> ids);

    R setType(Student student);

    R adminLogin(Student student);

    R updatePassword(Student student);

    R forgetPassword(Student student);

    R queryStudentListByClassNumber(Student student);

    R getStudentById(Long id);

    R queryListByIds(Student student, List<Long> ids);

    R getClassNumberListOfStudentAdmin();

    R login(Student student);

    R getClassNumberList();

    Page<Student> queryPage(QueryPageWithStudentAdminStudentStatusRecordDto queryPageWithStudentAdminStudentStatusRecordDto);
}
