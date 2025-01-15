package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudent Controller
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:49
 */
@RestController
@RequestMapping("/studentAdminStudent")
public class StudentAdminStudentController {
    @Autowired
    private StudentAdminStudentService studentAdminStudentService;
}
