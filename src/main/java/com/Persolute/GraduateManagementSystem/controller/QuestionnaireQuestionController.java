package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.QuestionnaireQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description QuestionnaireQuestion Controller
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:01
 */
@RestController
@RequestMapping("/questionnaireQuestion")
public class QuestionnaireQuestionController {
    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;
}
