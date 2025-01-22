package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.admin.LoginDto;
import com.Persolute.GraduateManagementSystem.entity.dto.admin.RegisterDto;
import com.Persolute.GraduateManagementSystem.entity.dto.admin.UpdatePasswordDto;
import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.AdminService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

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
    public R register(@RequestBody RegisterDto registerDto) {
        if (registerDto.getAccount() == null) {
            return R.error("账号不能为空");
        } else if (registerDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Admin admin = new Admin();
        admin.setAccount(registerDto.getAccount());
        admin.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        admin.setHasNotLoginFlag(true);

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
    public R login(@RequestBody LoginDto loginDto) {
        if (loginDto.getAccount() == null) {
            return R.error("账号不能为空");
        } else if (loginDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Admin admin = new Admin();
        admin.setAccount(loginDto.getAccount());
        admin.setPassword(loginDto.getPassword());

        return adminService.login(admin);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新密码
     * @email 1538520381@qq.com
     * @date 2025/1/16 上午10:20
     */
    @PutMapping("/updatePassword")
    public R updatePassword(HttpServletRequest httpServletRequest, @RequestBody UpdatePasswordDto updatePasswordDto) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            return R.error("用户未登录");
        }

        String userId;
        try {
            Claims claims = JWTUtil.paresJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new CustomerException("非法token");
        }

        Admin admin = new Admin();
        admin.setId(Long.parseLong(userId));
        admin.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
        admin.setHasNotLoginFlag(false);

        return adminService.updatePassword(admin);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据token获取
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午1:53
     */
    @GetMapping("/getAdminByToken")
    public R getAdminByToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            return R.error("用户未登录");
        }

        String userId;
        try {
            Claims claims = JWTUtil.paresJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new CustomerException("非法token");
        }

        return adminService.getAdminById(Long.parseLong(userId));
    }
}
