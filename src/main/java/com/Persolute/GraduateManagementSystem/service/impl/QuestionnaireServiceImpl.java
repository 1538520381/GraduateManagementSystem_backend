package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.Persolute.GraduateManagementSystem.mapper.QuestionnaireMapper;
import com.Persolute.GraduateManagementSystem.service.QuestionnaireService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.segments.PageInfo;
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

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页获取
     * @email 1538520381@qq.com
     * @date 2025/3/9 上午10:14
     */
    @Override
    public Page<Questionnaire> getPage(int page, int pageSize) {
        LambdaQueryWrapper<Questionnaire> lambdaQueryWrapper = new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getIsDeleted, false);

        Page<Questionnaire> pageInfo = new Page<>(page, pageSize);
        super.page(pageInfo, lambdaQueryWrapper);
        return pageInfo;
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/3/9 上午10:52
     */
    @Override
    public void deleteById(Long id) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        questionnaire.setIsDeleted(true);
        super.updateById(questionnaire);
    }
}
