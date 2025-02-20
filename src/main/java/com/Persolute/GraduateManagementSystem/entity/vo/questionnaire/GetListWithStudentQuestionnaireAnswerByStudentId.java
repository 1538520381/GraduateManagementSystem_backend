package com.Persolute.GraduateManagementSystem.entity.vo.questionnaire;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description GetListWithStudentQuestionnaireAnswer VO
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:55
 */
@Data
public class GetListWithStudentQuestionnaireAnswerByStudentId extends Questionnaire {
    private List<QuestionnaireQuestion> questionnaireQuestionList;

    private List<StudentQuestionnaireAnswer> studentQuestionnaireAnswerList;
}
