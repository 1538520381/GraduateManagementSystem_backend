package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description Questionnaire Controller
 * @email 1538520381@qq.com
 * @date 2025/02/18 22:58
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;
}
