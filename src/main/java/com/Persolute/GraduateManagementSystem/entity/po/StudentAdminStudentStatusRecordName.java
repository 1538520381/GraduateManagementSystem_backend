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
 * @description 学生管理员学生状态记录名称表
 * @email 1538520381@qq.com
 * @date 2025/03/06 15:32
 */
@Data
@TableName("StudentAdminStudentStatusRecordName")
public class StudentAdminStudentStatusRecordName implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学生管理员学生状态记录日期表主键
    private Long studentAdminStudentStatusRecordDateId;

    // 题号
    private Integer sort;

    // 题干
    private String stem;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
