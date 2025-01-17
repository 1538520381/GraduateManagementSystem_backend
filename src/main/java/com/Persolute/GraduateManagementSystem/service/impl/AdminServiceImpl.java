package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.mapper.AdminMapper;
import com.Persolute.GraduateManagementSystem.service.AdminService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午2:46
     */
    @Override
    public R register(Admin registerAdmin) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<Admin>()
                .eq(Admin::getAccount, registerAdmin.getAccount());
        List<Admin> adminList = super.list(lambdaQueryWrapper);
        if (!adminList.isEmpty()) {
            return R.error("该账号已存在");
        }

        if (!super.save(registerAdmin)) {
            return R.error();
        }

        return R.success("注册成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 登录
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午2:53
     */
    @Override
    public R login(Admin loginAdmin) {
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<Admin>()
                .eq(Admin::getAccount, loginAdmin.getAccount());

        Admin admin = super.getOne(lambdaQueryWrapper);
        if (admin == null) {
            return R.error("账号不存在");
        }

        if (!passwordEncoder.matches(loginAdmin.getPassword(), admin.getPassword())) {
            return R.error("密码错误");
        }

        redisTemplate.opsForValue().set("login_" + admin.getId(), admin);

        String token = JWTUtil.createJWT(String.valueOf(admin.getId()));

        return R.success("登录成功").put("token", token).put("hasNotLoginFlag", admin.getHasNotLoginFlag());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新密码
     * @email 1538520381@qq.com
     * @date 2025/1/16 上午10:19
     */
    @Override
    public R updatePassword(Admin admin) {
        if (!super.updateById(admin)) {
            return R.error();
        }

        return R.success("更新成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据管理员id获取
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午12:02
     */
    @Override
    public R getAdminByAdminId(Long adminId) {
        Admin admin = getById(adminId);
        if (admin == null) {
            return R.error("用户不存在");
        }
        return R.success("获取成功").put("admin", admin);
    }
}
