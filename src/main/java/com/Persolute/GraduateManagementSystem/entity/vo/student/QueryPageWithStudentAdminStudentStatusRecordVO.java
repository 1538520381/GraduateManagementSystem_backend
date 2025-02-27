package com.Persolute.GraduateManagementSystem.entity.vo.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import lombok.Data;

import java.util.Date;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/27 14:44
 */
@Data
public class QueryPageWithStudentAdminStudentStatusRecordVO extends Student {
    private StudentAdminStudentStatusRecord studentAdminStudentStatusRecord;
//    // 学生管理员学生状态记录日期主键
//    private Long studentAdminStudentStatusRecordDateId;
//
//    // 在校标识
//    private Boolean onCampusFlag;
//
//    // 离校原因
//    private String leavingSchoolReason;
//
//    // 离校去向
//    private String leavingSchoolDestination;
//
//    // 科研进展
//    private String scientificResearchProgress;
//
//    // 性格特征
//    private String personalityTraits;
//
//    // 异常问题
//    private String abnormalIssues;


    private StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate;
//    // 开始时间
//    private Date startTime;
//
//    // 结束时间
//    private Date endTime;
//
//    // 学期
//    private String semester;
//
//    // 周
//    private String week;

    private Student studentAdmin;
//    private String adminStudentId;
//
//    private String adminStudentName;
}
