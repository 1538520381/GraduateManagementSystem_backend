package com.Persolute.GraduateManagementSystem.entity.dto.student;

import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/03/14 11:42
 */
@Data
public class QueryPageWithQuestionnaireQuestionDto extends Student {
    private Long questionnaireId;

    private Integer page;

    private Integer pageSize;
}
