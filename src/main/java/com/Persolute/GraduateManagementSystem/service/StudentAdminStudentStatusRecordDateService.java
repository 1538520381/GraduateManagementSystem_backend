package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordDate Service
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:01
 */
public interface StudentAdminStudentStatusRecordDateService extends IService<StudentAdminStudentStatusRecordDate> {
    R addList(List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDates);

    R getByNowTime();

    R getToNowTime();

    StudentAdminStudentStatusRecordDate getBySemesterAndWeek(String semester, String week);

    R getList();

    Page<StudentAdminStudentStatusRecordDate> getPage(Integer page, Integer pageSize);

    R deleteById(Long id);

    R add(StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate);

    List<StudentAdminStudentStatusRecordDate> getStudentAdminStudentStatusRecordDateList();

    Long addReturnId(StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate);
}
