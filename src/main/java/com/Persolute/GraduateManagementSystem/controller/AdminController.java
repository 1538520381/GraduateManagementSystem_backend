package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.AdminLoginDto;
import com.Persolute.GraduateManagementSystem.entity.dto.AdminRegisterDto;
import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Persolute
 * @version 1.0
 * @description Admin Controller
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:30
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @author Persolute
     * @version 1.0
     * @description 注册
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午1:29
     */
    @PostMapping("/register")
    public R register(@RequestBody AdminRegisterDto adminRegisterDto) {
        if (adminRegisterDto.getAccount() == null) {
            return R.error("账号不能为空");
        } else if (adminRegisterDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Admin admin = new Admin();
        admin.setAccount(adminRegisterDto.getAccount());
        admin.setPassword(passwordEncoder.encode(adminRegisterDto.getPassword()));

        return adminService.register(admin);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 登录
     * @email 1538520381@qq.com
     * @date 2025/1/15 下午2:53
     */
    @PostMapping("/login")
    public R login(@RequestBody AdminLoginDto adminLoginDto) {
        if (adminLoginDto.getAccount() == null) {
            return R.error("账号不能为空");
        } else if (adminLoginDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Admin admin = new Admin();
        admin.setAccount(adminLoginDto.getAccount());
        admin.setPassword(adminLoginDto.getPassword());

        return adminService.login(admin);
    }
}
