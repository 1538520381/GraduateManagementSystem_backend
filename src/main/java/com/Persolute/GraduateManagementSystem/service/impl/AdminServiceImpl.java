package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.AdminMapper;
import com.Persolute.GraduateManagementSystem.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Admin ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午2:46
     */
    @Override
    public R register(Admin admin) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<Admin>().eq(Admin::getAccount, admin.getAccount());
        List<Admin> adminList = this.list(lambdaQueryWrapper);
        if (!adminList.isEmpty()) {
            return R.error("该账号已存在");
        }

        if (!this.save(admin)) {
            return R.error();
        }

        return R.success("注册成功");
    }
}
