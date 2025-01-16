package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
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
}
