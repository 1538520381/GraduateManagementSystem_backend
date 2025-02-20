package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.mapper.QuestionnaireMapper;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Questionnaire ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/18 22:57
 */
@Service
public class QuestionnaireServiceImpl extends ServiceImpl<QuestionnaireMapper, Questionnaire> implements QuestionnaireService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表
     * @email 1538520381@qq.com
     * @date 2025/2/19 下午2:53
     */
    @Override
    public List<Questionnaire> getList() {
        LambdaQueryWrapper<Questionnaire> lambdaQueryWrapper = new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getIsDeleted, false);
        return super.list(lambdaQueryWrapper);
    }
}
