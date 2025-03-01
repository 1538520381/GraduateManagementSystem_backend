package com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordDate GetPage Dto
 * @email 1538520381@qq.com
 * @date 2025/03/01 14:38
 */
@Data
public class GetPageDto extends StudentAdminStudentStatusRecordDate {
    private Integer page;

    private Integer pageSize;
}
