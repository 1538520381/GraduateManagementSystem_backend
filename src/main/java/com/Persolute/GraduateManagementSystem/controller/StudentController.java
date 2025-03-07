package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.admin.LoginDto;
import com.Persolute.GraduateManagementSystem.entity.dto.student.*;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.student.QueryPageWithStudentAdminStudentStatusRecordVO;
import com.Persolute.GraduateManagementSystem.entity.vo.student.WithStudentAdminVO;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    @Autowired
    private StudentAdminStudentStatusRecordDateService studentAdminStudentStatusRecordDateService;
    @Autowired
    private StudentAdminStudentStatusRecordService studentAdminStudentStatusRecordService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生列表
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午1:28
     */
    @PostMapping("/addList")
    public R addList(@RequestBody AddListDto addListDto) {
        return studentService.addList(addListDto.getStudentList());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生分页
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @GetMapping("/queryPageWithStudentAdmin")
    public R queryPageWithStudentAdmin(QueryPageWithStudentAdminDto studentQueryListWithStudentAdminDto) {
        if (studentQueryListWithStudentAdminDto.getPage() == null) {
            throw new CustomerException("服务器异常");
        } else if (studentQueryListWithStudentAdminDto.getPageSize() == null) {
            throw new CustomerException("服务器异常");
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentQueryListWithStudentAdminDto, student);

        R r1 = studentService.queryPage(student, studentQueryListWithStudentAdminDto.getPage(), studentQueryListWithStudentAdminDto.getPageSize());
        Page<Student> studentPage = (Page<Student>) r1.get("pageInfo");
        List<WithStudentAdminVO> withStudentAdminVOList = studentPage.getRecords().stream().map((item) -> {
            WithStudentAdminVO withStudentAdminVO = new WithStudentAdminVO();
            BeanUtils.copyProperties(item, withStudentAdminVO);
            R r2 = studentAdminStudentService.getByStudentId(item.getId());
            if (r2.get("studentAdminId") != null) {
                R r3 = studentService.getStudentById((Long) r2.get("studentAdminId"));
                withStudentAdminVO.setStudentAdmin(r3.get("student") == null ? null : (Student) r3.get("student"));
            }
            return withStudentAdminVO;
        }).collect(Collectors.toList());

        Page<WithStudentAdminVO> withStudentAdminVOPage = new Page<>();
        BeanUtils.copyProperties(studentPage, withStudentAdminVOPage);
        withStudentAdminVOPage.setRecords(withStudentAdminVOList);

        return R.success().put("pageInfo", withStudentAdminVOPage);
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
    public R setType(@RequestBody SetTypeDto setTypeDto) {
        if (setTypeDto.getType() == null) {
            throw new CustomerException("服务器异常");
        } else if (setTypeDto.getId() == null) {
            throw new CustomerException("服务器异常");
        }

        Student student = new Student();
        BeanUtils.copyProperties(setTypeDto, student);
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
    public R adminLogin(@RequestBody AdminLoginDto adminLoginDto) {
        if (adminLoginDto.getStudentNumber() == null) {
            throw new CustomerException("学号不能为空");
        } else if (adminLoginDto.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        }

        Student student = new Student();
        student.setStudentNumber(adminLoginDto.getStudentNumber());
        student.setPassword(adminLoginDto.getPassword());
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
    public R updatePassword(HttpServletRequest httpServletRequest, @RequestBody UpdatePasswordDto updatePasswordDto) {
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
        student.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));
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
    public R forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
        if (forgetPasswordDto.getStudentNumber() == null) {
            throw new CustomerException("学号不能为空");
        } else if (forgetPasswordDto.getIdNumber() == null) {
            throw new CustomerException("身份证号不能为空");
        } else if (forgetPasswordDto.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        }

        Student student = new Student();
        student.setStudentNumber(forgetPasswordDto.getStudentNumber());
        student.setIdNumber(forgetPasswordDto.getIdNumber());
        student.setPassword(passwordEncoder.encode(forgetPasswordDto.getPassword()));
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
    public R queryStudentListByClassNumberWithStudentAdmin(QueryListDto queryListDto) {
        if (queryListDto.getClassNumber() == null) {
            throw new CustomerException("服务器异常");
        }

        Student student = new Student();
        student.setStudentNumber(queryListDto.getStudentNumber());
        student.setName(queryListDto.getName());
        student.setClassNumber(queryListDto.getClassNumber());

        R r1 = studentService.queryStudentListByClassNumber(student);
        List<WithStudentAdminVO> withStudentAdminVOList = ((List<Student>) r1.get("studentList")).stream().map((item) -> {
            WithStudentAdminVO withStudentAdminVO = new WithStudentAdminVO();
            BeanUtils.copyProperties(item, withStudentAdminVO);
            R r2 = studentAdminStudentService.getByStudentId(item.getId());
            if (r2.get("studentAdminId") != null) {
                R r3 = studentService.getStudentById((Long) r2.get("studentAdminId"));
                withStudentAdminVO.setStudentAdmin(r3.get("student") == null ? null : (Student) r3.get("student"));
            }
            return withStudentAdminVO;
        }).collect(Collectors.toList());

        return R.success().put("studentList", withStudentAdminVOList);
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

    /*
     * @author Persolute
     * @version 1.0
     * @description 查询学生列表根据学生管理员id
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午2:10
     */
    @GetMapping("/queryListByStudentAdminId")
    public R queryListByStudentAdminId(QueryListByStudentAdminIdDto queryListByStudentAdminIdDto) {
        if (queryListByStudentAdminIdDto.getStudentAdminId() == null) {
            throw new CustomerException("服务器异常");
        }

        List<Long> studentIdList = (List<Long>) studentAdminStudentService.getStudentIdListByStudentAdminId(queryListByStudentAdminIdDto.getStudentAdminId()).get("studentIdList");

        Student student = new Student();
        student.setStudentNumber(queryListByStudentAdminIdDto.getStudentNumber());
        student.setName(queryListByStudentAdminIdDto.getName());

        return studentService.queryListByIds(student, studentIdList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生管理员班级号列表
     * @email 1538520381@qq.com
     * @date 2025/2/11 下午8:11
     */
    @GetMapping("/getClassNumberListOfStudentAdmin")
    public R getClassNumberListOfStudentAdmin() {
        return studentService.getClassNumberListOfStudentAdmin();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 学生登录
     * @email 1538520381@qq.com
     * @date 2025/2/24 下午4:04
     */
    @PostMapping("/login")
    public R login(@RequestBody Student student) {
        if (student.getStudentNumber() == null) {
            throw new CustomerException("账号不能为空");
        } else if (student.getPassword() == null) {
            throw new CustomerException("密码不能为空");
        }

        return studentService.login(student);
    }


    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生班级号列表
     * @email 1538520381@qq.com
     * @date 2025/2/24 下午4:04
     */
    @GetMapping("/getClassNumberList")
    public R getClassNumberList() {
        return studentService.getClassNumberList();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页查询
     * @email 1538520381@qq.com
     * @date 2025/3/3 下午4:26
     */
    @GetMapping("/queryPageWithStudentAdminStudentStatusRecord")
    public R queryPageWithStudentAdminStudentStatusRecord(QueryPageWithStudentAdminStudentStatusRecordDto queryPageWithStudentAdminStudentStatusRecordDto) {
        if (queryPageWithStudentAdminStudentStatusRecordDto.getPage() == null) {
            throw new CustomerException("服务器异常");
        } else if (queryPageWithStudentAdminStudentStatusRecordDto.getPageSize() == null) {
            throw new CustomerException("服务器异常");
        } else if (queryPageWithStudentAdminStudentStatusRecordDto.getSemester() == null) {
            throw new CustomerException();
        } else if (queryPageWithStudentAdminStudentStatusRecordDto.getWeek() == null) {
            throw new CustomerException();
        }

        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = studentAdminStudentStatusRecordDateService.getBySemesterAndWeek(queryPageWithStudentAdminStudentStatusRecordDto.getSemester(), queryPageWithStudentAdminStudentStatusRecordDto.getWeek());

        if (studentAdminStudentStatusRecordDate == null) {
            return R.success().put("pageInfo", new Page<>(queryPageWithStudentAdminStudentStatusRecordDto.getPage(), queryPageWithStudentAdminStudentStatusRecordDto.getPageSize()));
        }

        Page<Student> studentPage = studentService.queryPage(queryPageWithStudentAdminStudentStatusRecordDto);
        List<QueryPageWithStudentAdminStudentStatusRecordVO> queryPageWithStudentAdminStudentStatusRecordVOList = studentPage.getRecords().stream().map((item) -> {
            QueryPageWithStudentAdminStudentStatusRecordVO queryPageWithStudentAdminStudentStatusRecordVO = new QueryPageWithStudentAdminStudentStatusRecordVO();
            BeanUtils.copyProperties(item, queryPageWithStudentAdminStudentStatusRecordVO);

            Long adminStudentId = studentAdminStudentService.getAdminStudentIdByStudentId(item.getId());
            if (adminStudentId != null) {
                queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdmin(studentService.getById(adminStudentId));
            }

            queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdminStudentStatusRecordDate(studentAdminStudentStatusRecordDate);
            queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdminStudentStatusRecord(studentAdminStudentStatusRecordService.getStudentAdminStudentStatusRecordByStudentIdAndStudentAdminStudentStatusRecordDateId(item.getId(), studentAdminStudentStatusRecordDate.getId()));

            return queryPageWithStudentAdminStudentStatusRecordVO;
        }).collect(Collectors.toList());

        Page<QueryPageWithStudentAdminStudentStatusRecordVO> queryPageWithStudentAdminStudentStatusRecordVOPage = new Page<>();
        BeanUtils.copyProperties(studentPage, queryPageWithStudentAdminStudentStatusRecordVOPage);
        queryPageWithStudentAdminStudentStatusRecordVOPage.setRecords(queryPageWithStudentAdminStudentStatusRecordVOList);

        return R.success().put("pageInfo", queryPageWithStudentAdminStudentStatusRecordVOPage);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 查询列表
     * @email 1538520381@qq.com
     * @date 2025/3/3 下午4:27
     */
    @GetMapping("/queryListWithStudentAdminStudentStatusRecord")
    public R queryListWithStudentAdminStudentStatusRecord(QueryPageWithStudentAdminStudentStatusRecordDto queryPageWithStudentAdminStudentStatusRecordDto) {
        if (queryPageWithStudentAdminStudentStatusRecordDto.getSemester() == null) {
            throw new CustomerException();
        } else if (queryPageWithStudentAdminStudentStatusRecordDto.getWeek() == null) {
            throw new CustomerException();
        }

        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = studentAdminStudentStatusRecordDateService.getBySemesterAndWeek(queryPageWithStudentAdminStudentStatusRecordDto.getSemester(), queryPageWithStudentAdminStudentStatusRecordDto.getWeek());

        if (studentAdminStudentStatusRecordDate == null) {
            return R.success().put("studentList", new ArrayList<>());
        }

        List<Student> studentList = studentService.queryList(queryPageWithStudentAdminStudentStatusRecordDto);
        List<QueryPageWithStudentAdminStudentStatusRecordVO> queryPageWithStudentAdminStudentStatusRecordVOList = studentList.stream().map((item) -> {
            QueryPageWithStudentAdminStudentStatusRecordVO queryPageWithStudentAdminStudentStatusRecordVO = new QueryPageWithStudentAdminStudentStatusRecordVO();
            BeanUtils.copyProperties(item, queryPageWithStudentAdminStudentStatusRecordVO);

            Long adminStudentId = studentAdminStudentService.getAdminStudentIdByStudentId(item.getId());
            if (adminStudentId != null) {
                queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdmin(studentService.getById(adminStudentId));
            }

            queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdminStudentStatusRecordDate(studentAdminStudentStatusRecordDate);
            queryPageWithStudentAdminStudentStatusRecordVO.setStudentAdminStudentStatusRecord(studentAdminStudentStatusRecordService.getStudentAdminStudentStatusRecordByStudentIdAndStudentAdminStudentStatusRecordDateId(item.getId(), studentAdminStudentStatusRecordDate.getId()));

            return queryPageWithStudentAdminStudentStatusRecordVO;
        }).collect(Collectors.toList());

        return R.success().put("studentList", queryPageWithStudentAdminStudentStatusRecordVOList);
    }
}
