package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.AdminDto;
import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.service.AdminService;
import org.springframework.beans.BeanUtils;
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
    public R register(@RequestBody AdminDto adminDto) {
        if (adminDto.getAccount() == null) {
            return R.error("账号不能为空");
        } else if (adminDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Admin admin = new Admin();
        admin.setAccount(adminDto.getAccount());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        return adminService.register(admin);
    }
}
