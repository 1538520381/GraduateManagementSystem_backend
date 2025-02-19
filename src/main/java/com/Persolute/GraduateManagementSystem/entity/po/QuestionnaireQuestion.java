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
 * @description 问卷题目
 * @email 1538520381@qq.com
 * @date 2025/02/18 22:59
 */
@TableName("QuestionnaireQuestion")
@Data
public class QuestionnaireQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 问卷模板主键
    private Long questionnaireTemplateId;

    // 题号
    private Integer questionNumber;

    // 题干
    private String stem;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
