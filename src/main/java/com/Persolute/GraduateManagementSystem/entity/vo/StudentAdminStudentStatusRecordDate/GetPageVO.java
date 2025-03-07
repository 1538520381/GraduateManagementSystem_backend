package com.Persolute.GraduateManagementSystem.entity.vo.StudentAdminStudentStatusRecordDate;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordName;
import lombok.Data;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description GetPage VO
 * @email 1538520381@qq.com
 * @date 2025/03/07 11:17
 */
@Data
public class GetPageVO extends StudentAdminStudentStatusRecordDate {
    private List<StudentAdminStudentStatusRecordName> problems;
}
