package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.StudentQuestionnaireAnswerMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentQuestionnaireAnswer ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:09
 */
@Service
public class StudentQuestionnaireAnswerService extends ServiceImpl<StudentQuestionnaireAnswerMapper, StudentQuestionnaireAnswer> implements com.Persolute.GraduateManagementSystem.service.StudentQuestionnaireAnswerService {
    @Override
    public List<StudentQuestionnaireAnswer> getListByQuestionnaireIdAndStudentId(Long questionnaireId, Long studentId) {
        LambdaQueryWrapper<StudentQuestionnaireAnswer> lambdaQueryWrapper = new LambdaQueryWrapper<StudentQuestionnaireAnswer>()
                .eq(StudentQuestionnaireAnswer::getIsDeleted, false)
                .eq(StudentQuestionnaireAnswer::getQuestionnaireId, questionnaireId)
                .eq(StudentQuestionnaireAnswer::getStudentId, studentId);
        List<StudentQuestionnaireAnswer> studentQuestionnaireAnswerList = super.list(lambdaQueryWrapper);
        return studentQuestionnaireAnswerList;
    }
}
