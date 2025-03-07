package com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Add Dto
 * @email 1538520381@qq.com
 * @date 2025/03/07 11:07
 */
@Data
public class AddDto extends StudentAdminStudentStatusRecordDate {
    private List<String> problems;
}
