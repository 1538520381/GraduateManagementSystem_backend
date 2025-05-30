package com.Persolute.GraduateManagementSystem.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord PO
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:04
 */
@TableName("StudentAdminStudentStatusRecord")
@Data
public class StudentAdminStudentStatusRecord implements Serializable {
    private final static long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学生管理员主键
    private Long studentAdminId;

    // 学生主键
    private Long studentId;

    // 学生管理员学生状态记录日期主键
    private Long studentAdminStudentStatusRecordDateId;

    // 在校标识
    private Boolean onCampusFlag;

    // 离校原因
    private String leavingSchoolReason;

    // 离校去向
    private String leavingSchoolDestination;

    // 科研进展
    private String problem1;

    // 性格特征
    private String problem2;

    // 异常问题
    private String problem3;

    private String problem4;
    private String problem5;
    private String problem6;
    private String problem7;
    private String problem8;
    private String problem9;
    private String problem10;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
