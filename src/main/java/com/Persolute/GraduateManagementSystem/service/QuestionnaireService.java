package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.Questionnaire;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Questionnaire Service
 * @email 1538520381@qq.com
 * @date 2025/02/18 22:57
 */
public interface QuestionnaireService extends IService<Questionnaire> {
    List<Questionnaire> getList();

    Page<Questionnaire> getPage(int page, int pageSize);
}
