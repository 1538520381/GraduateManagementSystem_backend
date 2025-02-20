package com.Persolute.GraduateManagementSystem.entity.dto.StudentQuestionnaireAnswer;

import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description addList Dto
 * @email 1538520381@qq.com
 * @date 2025/02/20 14:36
 */
@Data
public class AddListDto {
    private List<StudentQuestionnaireAnswer> studentQuestionnaireAnswerList;
}
