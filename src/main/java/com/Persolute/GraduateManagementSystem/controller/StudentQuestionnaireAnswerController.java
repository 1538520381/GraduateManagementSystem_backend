package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.StudentQuestionnaireAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:03
 */
@RestController
@RequestMapping("/studentQuestionnaireAnswer")
public class StudentQuestionnaireAnswerController {
    @Autowired
    private StudentQuestionnaireAnswerService studentQuestionnaireAnswerService;
}
