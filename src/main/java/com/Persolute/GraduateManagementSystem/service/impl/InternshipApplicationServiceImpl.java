package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.InternshipApplicationMapper;
import com.Persolute.GraduateManagementSystem.service.InternshipApplicationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
