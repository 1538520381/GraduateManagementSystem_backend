package com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:18
 */
@Data
public class AddByStartTimeAndCycleLengthAndCycleNumberDto extends StudentAdminStudentStatusRecordDate {
    // 周期长度
    private Integer cycleLength;

    // 周期数
    private Integer cycleNumber;
}
