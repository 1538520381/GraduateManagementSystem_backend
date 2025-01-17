package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.StudentAddListErrorVo;
import com.Persolute.GraduateManagementSystem.mapper.StudentMapper;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description Student ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:42
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生列表
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @Override
    public R addList(List<Student> studentList) {
        List<StudentAddListErrorVo> errorList = new ArrayList<>();
        List<Student> successList = new ArrayList<>();

        for (Student student : studentList) {
            if (student.getStudentNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("学号不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getName() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("姓名不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getClassNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("班级号不能为空");
                errorList.add(studentAddListErrorVo);
            } else if (student.getIdNumber() == null) {
                StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                BeanUtils.copyProperties(student, studentAddListErrorVo);
                studentAddListErrorVo.setErrorMessage("身份证号（后六位）不能为空");
                errorList.add(studentAddListErrorVo);
            } else {
                LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>().eq(Student::getStudentNumber, student.getStudentNumber());
                if (super.getOne(lambdaQueryWrapper) != null) {
                    StudentAddListErrorVo studentAddListErrorVo = new StudentAddListErrorVo();
                    BeanUtils.copyProperties(student, studentAddListErrorVo);
                    studentAddListErrorVo.setErrorMessage("该学号学生已存在");
                    errorList.add(studentAddListErrorVo);
                } else {
                    student.setPassword(passwordEncoder.encode(student.getIdNumber()));
                    student.setType(0);
                    student.setHasNotLoginFlag(true);
                    successList.add(student);
                }
            }
        }
        if (!successList.isEmpty()) {
            if (!super.saveBatch(successList)) {
                return R.error();
            }
        }

        return R.success().put("errorList", errorList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生分页
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @Override
    public R queryPage(Student student, Integer page, Integer pageSize) {
        Page<Student> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (student.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, student.getStudentNumber());
        }

        if (student.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, student.getName());
        }

        if (student.getClassNumber() != null) {
            lambdaQueryWrapper.like(Student::getClassNumber, student.getClassNumber());
        }

        if (student.getType() != null) {
            lambdaQueryWrapper.eq(Student::getType, student.getType());
        }

        super.page(pageInfo, lambdaQueryWrapper);

        return R.success().put("pageInfo", pageInfo);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:29
     */
    @Override
    public R deleteById(Long id) {
        if (!super.removeById(id)) {
            return R.error();
        }
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据ids删除学生
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午7:54
     */
    @Override
    public R deleteByIds(List<Long> ids) {
        if (!super.removeByIds(ids)) {
            return R.error();
        }
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 设置学生类型
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午9:59
     */
    @Override
    public R setType(Student student) {
        if (!super.updateById(student)) {
            return R.error();
        }
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 学生管理员登录
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午10:46
     */
    @Override
    public R adminLogin(Student loginStudent) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>().eq(Student::getStudentNumber, loginStudent.getStudentNumber());
        Student student = super.getOne(lambdaQueryWrapper);

        if (student == null) {
            return R.error("账号不存在");
        }

        if (student.getType() != 1) {
            return R.error("该账号非学生管理员账号");
        }

        if (!passwordEncoder.matches(loginStudent.getPassword(), student.getPassword())) {
            return R.error("密码错误");
        }

        redisTemplate.opsForValue().set("login_" + student.getId(), student);

        String token = JWTUtil.createJWT(String.valueOf(student.getId()));

        return R.success("登录成功").put("token", token).put("hasNotLoginFlag", student.getHasNotLoginFlag());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新密码
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午11:20
     */
    @Override
    public R updatePassword(Student student) {
        if (!super.updateById(student)) {
            return R.error();
        }

        return R.success("更新成功");
    }
}
