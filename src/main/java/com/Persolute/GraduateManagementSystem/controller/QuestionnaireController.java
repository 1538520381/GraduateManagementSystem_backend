package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.questionnaire.AddDto;
import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.questionnaire.GetListWithStudentQuestionnaireAnswerByStudentId;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireQuestionService;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireService;
import com.Persolute.GraduateManagementSystem.service.StudentQuestionnaireAnswerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<Questionnaire> questionnaireList = questionnaireService.getList();
        List<GetListWithStudentQuestionnaireAnswerByStudentId> getListWithStudentQuestionnaireAnswerListByStudentId = questionnaireList.stream().map((item) -> {
            GetListWithStudentQuestionnaireAnswerByStudentId getListWithStudentQuestionnaireAnswerByStudentId = new GetListWithStudentQuestionnaireAnswerByStudentId();
            BeanUtils.copyProperties(item, getListWithStudentQuestionnaireAnswerByStudentId);
            getListWithStudentQuestionnaireAnswerByStudentId.setQuestionnaireQuestionList(questionnaireQuestionService.getListQuestionnaireQuestionByQuestionnaireTemplateId(item.getQuestionnaireTemplateId()));
            getListWithStudentQuestionnaireAnswerByStudentId.setStudentQuestionnaireAnswerList(studentQuestionnaireAnswerService.getListByQuestionnaireIdAndStudentId(item.getId(), studentId));
            return getListWithStudentQuestionnaireAnswerByStudentId;
        }).collect(Collectors.toList());
        return R.success().put("questionnaireList", getListWithStudentQuestionnaireAnswerListByStudentId);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页获取
     * @email 1538520381@qq.com
     * @date 2025/3/9 上午9:17
     */
    @GetMapping("/getPage")
    public R getPage(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return R.success().put("pageInfo", questionnaireService.getPage(page, pageSize));
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增
     * @email 1538520381@qq.com
     * @date 2025/3/9 上午10:22
     */
    @PostMapping("/add")
    public R add(@RequestBody AddDto addDto) {
        if (addDto.getName() == null) {
            throw new CustomerException("问卷名不能为空");
        } else if (addDto.getStartTime() == null) {
            throw new CustomerException("开始时间不能为空");
        } else if (addDto.getEndTime() == null) {
            throw new CustomerException("结束时间不能为空");
        } else if (addDto.getStartTime().compareTo(addDto.getEndTime()) >= 0) {
            throw new CustomerException("开始时间要在结束时间之前");
        }

        Questionnaire questionnaire = new Questionnaire();
        BeanUtils.copyProperties(addDto, questionnaire);
        questionnaire.setQuestionnaireTemplateId(1L);
        questionnaireService.save(questionnaire);
        questionnaire.setQuestionnaireTemplateId(questionnaire.getId());
        questionnaireService.updateById(questionnaire);

        List<QuestionnaireQuestion> questionnaireQuestionList = new ArrayList<>();
        for (int i = 0; i < addDto.getQuestionList().size(); i++) {
            QuestionnaireQuestion questionnaireQuestion = new QuestionnaireQuestion();
            questionnaireQuestion.setQuestionnaireTemplateId(questionnaire.getId());
            questionnaireQuestion.setQuestionNumber(i + 1);
            questionnaireQuestion.setStem(addDto.getQuestionList().get(i));
            questionnaireQuestionList.add(questionnaireQuestion);
        }
        questionnaireQuestionService.saveBatch(questionnaireQuestionList);

        return R.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id) {
        questionnaireService.deleteById(id);
        return R.success();
    }
}
