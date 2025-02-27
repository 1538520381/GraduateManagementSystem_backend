package com.Persolute.GraduateManagementSystem.entity.dto.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/27 14:36
 */
@Data
public class QueryPageWithStudentAdminStudentStatusRecordDto extends Student {
    private Integer page;

    private Integer pageSize;

    private String semester;

    private String week;
}
