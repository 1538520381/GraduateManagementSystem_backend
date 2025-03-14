package com.Persolute.GraduateManagementSystem.entity.vo.questionnaire;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description getPage VO
 * @email 1538520381@qq.com
 * @date 2025/03/09 11:02
 */
@Data
public class GetPageVO extends Questionnaire {
    List<QuestionnaireQuestion> questionnaireQuestionList;
}
