package com.Persolute.GraduateManagementSystem.service;

import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Persolute
 * @version 1.0
 * @description Admin Service
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:28
 */
public interface AdminService extends IService<Admin> {
    R register(Admin admin);

    R login(Admin admin);
}
