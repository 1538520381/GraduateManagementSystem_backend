package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.dto.InternshipApplication.QueryPageWithoutStatus1WithStudentAndDocumentDto;
import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.mapper.InternshipApplicationMapper;
import com.Persolute.GraduateManagementSystem.service.InternshipApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description InternshipApplication ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:14
 */
@Service
public class InternshipApplicationServiceImpl extends ServiceImpl<InternshipApplicationMapper, InternshipApplication> implements InternshipApplicationService {
    @Autowired
    private InternshipApplicationMapper internshipApplicationMapper;

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据学生主键列表
     * @email 1538520381@qq.com
     * @date 2025/2/12 下午1:48
     */
    @Override
    public List<InternshipApplication> listByStudentIdWithDocumentSortByCreateTime(Long studentId) {
        LambdaQueryWrapper<InternshipApplication> lambdaQueryWrapper = new LambdaQueryWrapper<InternshipApplication>()
                .eq(InternshipApplication::getIsDeleted, false)
                .eq(InternshipApplication::getStudentId, studentId)
                .orderByDesc(InternshipApplication::getCreateTime);
        return super.list(lambdaQueryWrapper);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页查询除状态1
     * @email 1538520381@qq.com
     * @date 2025/2/12 下午3:23
     */
    @Override
    public Page<InternshipApplication> queryPageWithoutStatus1WithStudentAndDocument(QueryPageWithoutStatus1WithStudentAndDocumentDto queryPageWithoutStatus1WithStudentAndDocumentDto) {
        MPJLambdaWrapper<InternshipApplication> mpjLambdaWrapper = new MPJLambdaWrapper<InternshipApplication>()
                .selectAll(InternshipApplication.class)
                .leftJoin(Student.class, Student::getId, InternshipApplication::getStudentId)
                .eq(InternshipApplication::getIsDeleted, false)
                .ne(InternshipApplication::getStatus, 1)
                .orderByDesc(InternshipApplication::getCreateTime);

        if (queryPageWithoutStatus1WithStudentAndDocumentDto.getStudentNumber() != null) {
            mpjLambdaWrapper.like(Student::getStudentNumber, queryPageWithoutStatus1WithStudentAndDocumentDto.getStudentNumber());
        }
        if (queryPageWithoutStatus1WithStudentAndDocumentDto.getName() != null) {
            mpjLambdaWrapper.like(Student::getName, queryPageWithoutStatus1WithStudentAndDocumentDto.getName());
        }
        if (queryPageWithoutStatus1WithStudentAndDocumentDto.getClassNumber() != null) {
            mpjLambdaWrapper.eq(Student::getClassNumber, queryPageWithoutStatus1WithStudentAndDocumentDto.getClassNumber());
        }
        if (queryPageWithoutStatus1WithStudentAndDocumentDto.getStatus() != null) {
            mpjLambdaWrapper.eq(InternshipApplication::getStatus, queryPageWithoutStatus1WithStudentAndDocumentDto.getStatus());
        }

        Page<InternshipApplication> internshipApplicationPage = new Page<>(queryPageWithoutStatus1WithStudentAndDocumentDto.getPage(), queryPageWithoutStatus1WithStudentAndDocumentDto.getPageSize());
        internshipApplicationMapper.selectJoinPage(internshipApplicationPage, InternshipApplication.class, mpjLambdaWrapper);

        return internshipApplicationPage;
    }
}
