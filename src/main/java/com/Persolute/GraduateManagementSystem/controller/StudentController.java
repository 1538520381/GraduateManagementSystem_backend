package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.*;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.StudentWithStudentAdminVO;
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
import java.util.stream.Collectors;

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
    public R queryPage(StudentQueryPageDto studentQueryListDto) {
        if (studentQueryListDto.getPage() == null) {
            throw new CustomerException("服务器异常");
        } else if (studentQueryListDto.getPageSize() == null) {
            throw new CustomerException("服务器异常");
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
    @Transactional
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id) {
        R r = studentService.deleteById(id);
        studentAdminStudentService.deleteByStudentAdminId(id);
        studentAdminStudentService.deleteByStudentId(id);
        return r;
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据ids删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:54
     */
    @Transactional
    @DeleteMapping("/deleteByIds/{ids}")
    public R deleteByIds(@PathVariable List<Long> ids) {
        R r = studentService.deleteByIds(ids);
        studentAdminStudentService.deleteByStudentAdminIds(ids);
        studentAdminStudentService.deleteByStudentIds(ids);
        return r;
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
            throw new CustomerException("服务器异常");
        } else if (studentSetTypeDto.getId() == null) {
            throw new CustomerException("服务器异常");
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentSetTypeDto, student);
        R r = studentService.setType(student);
        if (student.getType() == 0) {
            studentAdminStudentService.deleteByStudentAdminId(student.getId());
        } else if (student.getType() == 1) {
            StudentAdminStudent studentAdminStudent = new StudentAdminStudent();
            studentAdminStudent.setStudentAdminId(student.getId());
            studentAdminStudent.setStudentId(student.getId());
            studentAdminStudentService.addStudentAdminStudent(studentAdminStudent);
        }
        return r;
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
            throw new CustomerException("学号不能为空");
        } else if (studentAdminLoginDto.getPassword() == null) {
            throw new CustomerException("密码不能为空");
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
    public R updatePassword(HttpServletRequest httpServletRequest, @RequestBody StudentUpdatePasswordDto studentUpdatePasswordDto) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            throw new CustomerException("用户未登录");
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
        student.setPassword(passwordEncoder.encode(studentUpdatePasswordDto.getPassword()));
        student.setHasNotLoginFlag(false);

        return studentService.updatePassword(student);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 忘记密码
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午12:00
     */
    @PutMapping("/forgetPassword")
    public R forgetPassword(@RequestBody StudentForgetPasswordDto studentForgetPasswordDto) {
        if (studentForgetPasswordDto.getStudentNumber() == null) {
            throw new CustomerException("学号不能为空");
        } else if (studentForgetPasswordDto.getIdNumber() == null) {
            throw new CustomerException("身份证号不能为空");
        } else if (studentForgetPasswordDto.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        }

        Student student = new Student();
        student.setStudentNumber(studentForgetPasswordDto.getStudentNumber());
        student.setIdNumber(studentForgetPasswordDto.getIdNumber());
        student.setPassword(passwordEncoder.encode(studentForgetPasswordDto.getPassword()));
        student.setHasNotLoginFlag(false);

        return studentService.forgetPassword(student);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生根据班级号携带学生管理员
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午4:14
     */
    @GetMapping("/queryStudentListByClassNumberWithStudentAdmin")
    public R queryStudentListByClassNumberWithStudentAdmin(StudentQueryListDto studentQueryListDto) {
        if (studentQueryListDto.getClassNumber() == null) {
            throw new CustomerException("服务器异常");
        }

        Student student = new Student();
        student.setStudentNumber(studentQueryListDto.getStudentNumber());
        student.setName(studentQueryListDto.getName());
        student.setClassNumber(studentQueryListDto.getClassNumber());

        R r1 = studentService.queryStudentListByClassNumber(student);
        List<StudentWithStudentAdminVO> studentWithStudentAdminVOList = ((List<Student>) r1.get("studentList")).stream().map((item) -> {
            StudentWithStudentAdminVO studentWithStudentAdminVO = new StudentWithStudentAdminVO();
            BeanUtils.copyProperties(item, studentWithStudentAdminVO);
            R r2 = studentAdminStudentService.getByStudentId(item.getId());
            if (r2.get("studentAdminId") != null) {
                R r3 = studentService.getStudentById((Long) r2.get("studentAdminId"));
                studentWithStudentAdminVO.setStudentAdmin(r3.get("student") == null ? null : (Student) r3.get("student"));
            }
            return studentWithStudentAdminVO;
        }).collect(Collectors.toList());

        return R.success().put("studentList", studentWithStudentAdminVOList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生通过token
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午5:19
     */
    @GetMapping("/getStudentByToken")
    public R getStudentByToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization");

        if (token == null) {
            throw new CustomerException("用户未登录");
        }

        String userId;
        try {
            Claims claims = JWTUtil.paresJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new CustomerException("非法token");
        }

        return studentService.getStudentById(Long.parseLong(userId));
    }
}
