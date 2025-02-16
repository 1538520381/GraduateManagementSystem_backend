package com.Persolute.GraduateManagementSystem.entity.vo.InternshipApplication;

import com.Persolute.GraduateManagementSystem.entity.po.Document;
import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2025/02/12 15:26
 */
@Data
public class QueryPageWithoutStatus1WithStudentAndDocumentVO extends InternshipApplication {
    private Student student;

    // 实习申请单文件
    private Document internshipApplicationFormDocument;

    // 家长知情书文件
    private Document parentalNotificationLetterDocument;
}
