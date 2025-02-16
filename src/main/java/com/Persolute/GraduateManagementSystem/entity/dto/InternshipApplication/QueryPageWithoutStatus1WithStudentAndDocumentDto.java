package com.Persolute.GraduateManagementSystem.entity.dto.InternshipApplication;

import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description queryPageWithoutType1 Dto
 * @email 1538520381@qq.com
 * @date 2025/02/12 15:00
 */
@Data
public class QueryPageWithoutStatus1WithStudentAndDocumentDto extends InternshipApplication {
    private Integer page;

    private Integer pageSize;

    // 学号
    private String studentNumber;

    // 姓名
    private String name;

    // 班级号
    private String classNumber;
}
