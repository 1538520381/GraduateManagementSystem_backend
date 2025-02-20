package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.StudentQuestionnaireAnswer.AddListDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentQuestionnaireAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/addList")
    public R addList(@RequestBody AddListDto addListDto) {
        if (addListDto.getStudentQuestionnaireAnswerList().isEmpty()) {
            throw new CustomerException();
        }
        return studentQuestionnaireAnswerService.addList(addListDto.getStudentQuestionnaireAnswerList());
    }
}
