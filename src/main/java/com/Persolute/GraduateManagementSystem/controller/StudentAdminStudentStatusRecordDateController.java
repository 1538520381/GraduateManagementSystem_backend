package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordDate Controller
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:03
 */
@RestController
@RequestMapping("/studentAdminStudentStatusRecordDate")
public class StudentAdminStudentStatusRecordDateController {
    @Autowired
    private StudentAdminStudentStatusRecordDateService studentAdminStudentStatusRecordDateService;
}
