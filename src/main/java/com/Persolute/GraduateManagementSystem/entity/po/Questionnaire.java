package com.Persolute.GraduateManagementSystem.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Persolute
 * @version 1.0
 * @description 问卷
 * @email 1538520381@qq.com
 * @date 2025/02/18 22:54
 */
@TableName("Questionnaire")
@Data
public class Questionnaire implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 问卷模板主键
    private Long questionnaireTemplateId;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
