package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Document;
import com.Persolute.GraduateManagementSystem.mapper.DocumentMapper;
import com.Persolute.GraduateManagementSystem.service.DocumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Persolute
 * @version 1.0
 * @description Document ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:04
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements DocumentService {
}
