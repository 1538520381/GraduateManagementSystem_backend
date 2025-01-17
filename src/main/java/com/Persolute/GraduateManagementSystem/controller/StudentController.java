package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.*;
import com.Persolute.GraduateManagementSystem.entity.po.Admin;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentService;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Student Service
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:43
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentAdminStudentService studentAdminStudentService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生列表
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午1:28
     */
    @PostMapping("/addList")
    public R addList(@RequestBody StudentAddListDto studentAddListDto) {
        return studentService.addList(studentAddListDto.getStudentList());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生分页
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @GetMapping("/queryPage")
    public R queryPage(StudentQueryListDto studentQueryListDto) {
        if (studentQueryListDto.getPage() == null) {
            return R.error();
        } else if (studentQueryListDto.getPageSize() == null) {
            return R.error();
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentQueryListDto, student);
        return studentService.queryPage(student, studentQueryListDto.getPage(), studentQueryListDto.getPageSize());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:29
     */
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id) {
        return studentService.deleteById(id);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据ids删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:54
     */
    @DeleteMapping("/deleteByIds/{ids}")
    public R deleteByIds(@PathVariable List<Long> ids) {
        return studentService.deleteByIds(ids);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 设置学生类型
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午9:56
     */
    @Transactional
    @PutMapping("/setType")
    public R setType(@RequestBody StudentSetTypeDto studentSetTypeDto) {
        if (studentSetTypeDto.getType() == null) {
            return R.error();
        } else if (studentSetTypeDto.getId() == null) {
            return R.error();
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentSetTypeDto, student);
        R r1 = studentService.setType(student);
        if ((Integer) r1.get("code") == 200) {
            if (student.getType() == 0) {
                R r2 = studentAdminStudentService.deleteByStudentAdminId(student.getId());
                if ((Integer) r2.get("code") != 200) {
                    return r2;
                }
            }
        }
        return r1;
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 学生管理员登录
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午10:41
     */
    @PostMapping("/adminLogin")
    public R adminLogin(@RequestBody StudentAdminLoginDto studentAdminLoginDto) {
        if (studentAdminLoginDto.getStudentNumber() == null) {
            return R.error("学号不能为空");
        } else if (studentAdminLoginDto.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Student student = new Student();
        student.setStudentNumber(studentAdminLoginDto.getStudentNumber());
        student.setPassword(studentAdminLoginDto.getPassword());
        return studentService.adminLogin(student);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新密码
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午11:16
     */
    @PutMapping("/updatePassword")
    public R updatePassword(HttpServletRequest httpServletRequest, @RequestBody StudentAdminUpdatePasswordDto studentAdminUpdatePasswordDto) {
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

        Student student = new Student();
        student.setId(Long.parseLong(userId));
        student.setPassword(passwordEncoder.encode(studentAdminUpdatePasswordDto.getPassword()));
        student.setHasNotLoginFlag(false);

        return studentService.updatePassword(student);
    }
}
