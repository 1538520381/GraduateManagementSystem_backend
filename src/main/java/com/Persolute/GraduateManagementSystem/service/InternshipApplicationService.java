package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.dto.InternshipApplication.QueryPageWithoutStatus1WithStudentAndDocumentDto;
import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description InternshipApplication Service
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:14
 */
public interface InternshipApplicationService extends IService<InternshipApplication> {
    List<InternshipApplication> listByStudentIdWithDocumentSortByCreateTime(Long studentId);

    Page<InternshipApplication> queryPageWithoutStatus1WithStudentAndDocument(QueryPageWithoutStatus1WithStudentAndDocumentDto queryPageWithoutStatus1WithStudentAndDocumentDto);
}
