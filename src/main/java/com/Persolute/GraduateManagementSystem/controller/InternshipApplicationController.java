package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.InternshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:15
 */
@RestController
@RequestMapping("/internshipApplication")
public class InternshipApplicationController {
    @Autowired
    private InternshipApplicationService internshipApplicationService;
}
