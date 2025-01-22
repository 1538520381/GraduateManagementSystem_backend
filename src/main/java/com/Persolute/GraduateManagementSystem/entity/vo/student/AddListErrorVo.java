package com.Persolute.GraduateManagementSystem.entity.vo.student;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description Student AddListError Vo
 * @email 1538520381@qq.com
 * @date 2025/01/16 13:31
 */
@Data
public class AddListErrorVo extends Student {
    // 失败信息
    private String errorMessage;
}
