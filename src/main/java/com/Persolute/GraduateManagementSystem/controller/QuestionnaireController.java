package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.questionnaire.GetListWithStudentQuestionnaireAnswerByStudentId;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireQuestionService;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireService;
import com.Persolute.GraduateManagementSystem.service.StudentQuestionnaireAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private QuestionnaireQuestionService questionnaireQuestionService;

    @Autowired
    private StudentQuestionnaireAnswerService studentQuestionnaireAnswerService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表携带学生问卷答案
     * @email 1538520381@qq.com
     * @date 2025/2/19 下午3:03
     */
    @PostMapping("/getListWithStudentQuestionnaireAnswerByStudentId/{studentId}")
    public R getListWithStudentQuestionnaireAnswerByStudentId(@PathVariable Long studentId) {
        List<Questionnaire> questionnaireList = questionnaireService.list();
        List<GetListWithStudentQuestionnaireAnswerByStudentId> getListWithStudentQuestionnaireAnswerListByStudentId = questionnaireList.stream().map((item) -> {
            GetListWithStudentQuestionnaireAnswerByStudentId getListWithStudentQuestionnaireAnswerByStudentId = new GetListWithStudentQuestionnaireAnswerByStudentId();
            BeanUtils.copyProperties(item, getListWithStudentQuestionnaireAnswerByStudentId);
            getListWithStudentQuestionnaireAnswerByStudentId.setQuestionnaireQuestionList(questionnaireQuestionService.getListQuestionnaireQuestionByQuestionnaireTemplateId(item.getQuestionnaireTemplateId()));
            getListWithStudentQuestionnaireAnswerByStudentId.setStudentQuestionnaireAnswerList(studentQuestionnaireAnswerService.getListByQuestionnaireIdAndStudentId(item.getId(), studentId));
            return getListWithStudentQuestionnaireAnswerByStudentId;
        }).collect(Collectors.toList());
        return R.success().put("questionnaireList", getListWithStudentQuestionnaireAnswerListByStudentId);
    }
}
