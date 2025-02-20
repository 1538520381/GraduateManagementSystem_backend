package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentQuestionnaireAnswer Service
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:08
 */
public interface StudentQuestionnaireAnswerService extends IService<StudentQuestionnaireAnswer> {
    List<StudentQuestionnaireAnswer> getListByQuestionnaireIdAndStudentId(Long questionnaireId, Long studentId);
}
