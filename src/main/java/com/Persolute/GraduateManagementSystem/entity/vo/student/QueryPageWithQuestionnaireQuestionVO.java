package com.Persolute.GraduateManagementSystem.entity.vo.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentQuestionnaireAnswer;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/03/14 12:18
 */
@Data
public class QueryPageWithQuestionnaireQuestionVO extends Student {
    private List<StudentQuestionnaireAnswer> studentQuestionnaireAnswerList;

    private Student studentAdmin;
}
