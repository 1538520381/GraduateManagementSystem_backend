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
 * @description 学生问卷答案
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:03
 */
@TableName("StudentQuestionnaireAnswer")
@Data
public class StudentQuestionnaireAnswer implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学生主键
    private Long studentId;

    // 问卷主键
    private Long questionnaireId;

    // 问卷题目主键
    private Long questionnaireQuestionId;

    // 答案
    private String answer;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
