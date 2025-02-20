package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.QuestionnaireQuestion;
import com.Persolute.GraduateManagementSystem.mapper.QuestionnaireQuestionMapper;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireQuestionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description QuestionnaireQuestion ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/19 14:01
 */
@Service
public class QuestionnaireQuestionServiceImpl extends ServiceImpl<QuestionnaireQuestionMapper, QuestionnaireQuestion> implements QuestionnaireQuestionService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 根据问卷模板获取列表
     * @email 1538520381@qq.com
     * @date 2025/2/19 下午3:41
     */
    @Override
    public List<QuestionnaireQuestion> getListQuestionnaireQuestionByQuestionnaireTemplateId(Long questionnaireTemplateId) {
        LambdaQueryWrapper<QuestionnaireQuestion> lambdaQueryWrapper = new LambdaQueryWrapper<QuestionnaireQuestion>()
                .eq(QuestionnaireQuestion::getIsDeleted, false)
                .eq(QuestionnaireQuestion::getQuestionnaireTemplateId, questionnaireTemplateId);
        return super.list(lambdaQueryWrapper);
    }
}
