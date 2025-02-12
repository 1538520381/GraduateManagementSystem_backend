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
 * @description 实习申请 PO
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:06
 */
@TableName("InternshipApplication")
@Data
public class InternshipApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 学生主键
    private Long studentId;

    // 实习开始时间
    private Date internshipStartTime;

    // 实习结束时间
    private Date internshipEndTime;

    // 实习单位名称
    private String internshipUnit;

    // 是否校外住宿
    private Boolean offCampusHousingFlag;

    // 实习申请单文件主键
    private Long internshipApplicationFormDocumentId;

    // 家长知情书文件主键
    private Long parentalNotificationLetterDocumentId;

    // 状态（0：待审核；1：撤回；2：审核通过；3：审核驳回）
    private Integer status;

    // 备注
    private String remark;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 修改时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 删除标识
    private Boolean isDeleted;
}
