package com.Persolute.GraduateManagementSystem.entity.vo.InternshipApplication;

import com.Persolute.GraduateManagementSystem.entity.po.Document;
import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import lombok.Data;

/**
 * @author Persolute
 * @version 1.0
 * @description ListByStudentIdWithDocument VO
 * @email 1538520381@qq.com
 * @date 2025/02/12 14:10
 */
@Data
public class ListByStudentIdWithDocumentSortByCreateTimeVO extends InternshipApplication {
    // 实习申请单文件
    private Document internshipApplicationFormDocument;

    // 家长知情书文件
    private Document parentalNotificationLetterDocument;
}
