package com.Persolute.GraduateManagementSystem.entity.dto.questionnaire;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description add Dto
 * @email 1538520381@qq.com
 * @date 2025/03/09 10:17
 */
@Data
public class AddDto extends Questionnaire {
    private List<String> questionList;
}
