package com.Persolute.GraduateManagementSystem.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Persolute
 * @version 1.0
 * @description 学生管理员学生状态记录日期 PO
 * @email 1538520381@qq.com
 * @date 2025/01/18 14:58
 */
@TableName("StudentAdminStudentStatusRecordDate")
@Data
public class StudentAdminStudentStatusRecordDate implements Serializable {
    private final static long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    // 学期
    private String semester;

    // 周
    private String week;

    // 删除标识
    private Boolean isDeleted;
}
