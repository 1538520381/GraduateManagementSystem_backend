package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.controller.StudentController;
import com.Persolute.GraduateManagementSystem.entity.dto.student.QueryPageWithStudentAdminStudentStatusRecordDto;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.student.AddListErrorVo;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.mapper.StudentMapper;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private StudentController studentController;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生列表
     * @email 1538520381@qq.com
     * @date 2025/1/16 下午6:41
     */
    @Override
    public R addList(List<Student> studentList) {
        List<AddListErrorVo> errorList = new ArrayList<>();
        List<Student> successList = new ArrayList<>();

        for (Student student : studentList) {
            if (student.getStudentNumber() == null) {
                AddListErrorVo addListErrorVo = new AddListErrorVo();
                BeanUtils.copyProperties(student, addListErrorVo);
                addListErrorVo.setErrorMessage("学号不能为空");
                errorList.add(addListErrorVo);
            } else if (student.getName() == null) {
                AddListErrorVo addListErrorVo = new AddListErrorVo();
                BeanUtils.copyProperties(student, addListErrorVo);
                addListErrorVo.setErrorMessage("姓名不能为空");
                errorList.add(addListErrorVo);
            } else if (student.getClassNumber() == null) {
                AddListErrorVo addListErrorVo = new AddListErrorVo();
                BeanUtils.copyProperties(student, addListErrorVo);
                addListErrorVo.setErrorMessage("班级号不能为空");
                errorList.add(addListErrorVo);
            } else if (student.getIdNumber() == null) {
                AddListErrorVo addListErrorVo = new AddListErrorVo();
                BeanUtils.copyProperties(student, addListErrorVo);
                addListErrorVo.setErrorMessage("身份证号（后六位）不能为空");
                errorList.add(addListErrorVo);
            } else {
                LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                        .eq(Student::getIsDeleted, false)
                        .eq(Student::getStudentNumber, student.getStudentNumber());
                if (super.getOne(lambdaQueryWrapper) != null) {
                    AddListErrorVo addListErrorVo = new AddListErrorVo();
                    BeanUtils.copyProperties(student, addListErrorVo);
                    addListErrorVo.setErrorMessage("该学号学生已存在");
                    errorList.add(addListErrorVo);
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
                throw new CustomerException("服务器异常");
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

        MPJLambdaWrapper<Student> lambdaQueryWrapper = new MPJLambdaWrapper<Student>()
                .selectAll(Student.class)
                .leftJoin(StudentAdminStudent.class, (wrapper) ->
                        wrapper
                                .eq(StudentAdminStudent::getStudentId, Student::getId)
                                .and(subWrapper -> subWrapper.isNull(StudentAdminStudent::getIsDeleted)
                                        .or()
                                        .eq(StudentAdminStudent::getIsDeleted, false)))
                .eq(Student::getIsDeleted, false)
                .orderByAsc(
                        "CASE WHEN class_number = '学硕1班' THEN 1 " +
                                "WHEN class_number = '学硕2班' THEN 2 " +
                                "WHEN class_number = '专硕1班' THEN 3 " +
                                "WHEN class_number = '专硕2班' THEN 4 " +
                                "WHEN class_number = '专硕3班' THEN 5 " +
                                "WHEN class_number = '博士班' THEN 6 " +
                                "ELSE 7 END"
                )
                // 对不在列表中的班级号按班级名排序
                .orderByAsc("CASE WHEN class_number NOT IN ('学硕1班', '学硕2班', '专硕1班', '专硕2班', '专硕3班', '博士班') THEN class_number END")
                .orderByDesc(StudentAdminStudent::getStudentAdminId)
                .orderByDesc(Student::getType)
                .orderByAsc(Student::getStudentNumber);

        if (student.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, student.getStudentNumber());
        }

        if (student.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, student.getName());
        }

        if (student.getClassNumber() != null) {
            lambdaQueryWrapper.eq(Student::getClassNumber, student.getClassNumber());
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
        Student student = new Student();
        student.setId(id);
        student.setIsDeleted(true);
        if (!super.updateById(student)) {
            throw new CustomerException("服务器异常");
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
        List<Student> studentList = new ArrayList<>();
        for (Long id : ids) {
            Student student = new Student();
            student.setId(id);
            student.setIsDeleted(true);
            studentList.add(student);
        }
        if (!super.updateBatchById(studentList)) {
            throw new CustomerException("服务器异常");
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
            throw new CustomerException("服务器异常");
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
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false)
                .eq(Student::getStudentNumber, loginStudent.getStudentNumber());
        Student student = super.getOne(lambdaQueryWrapper);

        if (student == null) {
            throw new CustomerException("账号不存在");
        }

        if (student.getType() != 1) {
            throw new CustomerException("该账号非学生管理员账号");
        }

        if (!passwordEncoder.matches(loginStudent.getPassword(), student.getPassword())) {
            throw new CustomerException("密码错误");
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
            throw new CustomerException("服务器异常");
        }

        return R.success("更新成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 忘记密码
     * @email 1538520381@qq.com
     * @date 2025/1/17 上午11:40
     */
    @Override
    public R forgetPassword(Student forgetPasswordStudent) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false)
                .eq(Student::getStudentNumber, forgetPasswordStudent.getStudentNumber())
                .eq(Student::getIdNumber, forgetPasswordStudent.getIdNumber());
        Student student = super.getOne(lambdaQueryWrapper);

        if (student == null) {
            throw new CustomerException("学号或身份证号（后六位）错误");
        }

        student.setPassword(forgetPasswordStudent.getPassword());
        if (!super.updateById(student)) {
            throw new CustomerException("服务器异常");
        }

        return R.success("更新成功");
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 条件查询学生根据班级号
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午4:20
     */
    public R queryStudentListByClassNumber(Student student) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false);

        if (student.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, student.getStudentNumber());
        }

        if (student.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, student.getName());
        }

        if (student.getClassNumber() == null) {
            throw new CustomerException("服务器异常");
        }
        lambdaQueryWrapper.eq(Student::getClassNumber, student.getClassNumber());

        List<Student> studentList = super.list(lambdaQueryWrapper);
        return R.success().put("studentList", studentList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id获取学生
     * @email 1538520381@qq.com
     * @date 2025/1/17 下午5:01
     */
    @Override
    public R getStudentById(Long id) {
        return R.success().put("student", super.getById(id));
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 查询学生根据ids
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午2:21
     */
    @Override
    public R queryListByIds(Student student, List<Long> ids) {
        if (ids.isEmpty()) {
            return R.success().put("studentList", new ArrayList<>());
        }

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false)
                .in(Student::getId, ids);

        if (student.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, student.getStudentNumber());
        }
        if (student.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, student.getName());
        }

        List<Student> studentList = super.list(lambdaQueryWrapper);
        return R.success().put("studentList", studentList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生管理员班级号列表
     * @email 1538520381@qq.com
     * @date 2025/2/6 下午10:49
     */
    @Override
    public R getClassNumberListOfStudentAdmin() {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false)
                .eq(Student::getType, 1);
        List<Student> studentList = super.list(lambdaQueryWrapper);
        Set<String> classNumberSet = new HashSet<>();
        for (Student student : studentList) {
            classNumberSet.add(student.getClassNumber());
        }
        return R.success().put("classNumberSet", classNumberSet);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 登录
     * @email 1538520381@qq.com
     * @date 2025/2/11 下午9:35
     */
    @Override
    public R login(Student loginStudent) {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false)
                .eq(Student::getStudentNumber, loginStudent.getStudentNumber());
        Student student = super.getOne(lambdaQueryWrapper);

        if (student == null) {
            throw new CustomerException("账号不存在");
        }

        if (!passwordEncoder.matches(loginStudent.getPassword(), student.getPassword())) {
            throw new CustomerException("密码错误");
        }

        redisTemplate.opsForValue().set("login_" + student.getId(), student);

        String token = JWTUtil.createJWT(String.valueOf(student.getId()));

        return R.success("登录成功").put("token", token).put("hasNotLoginFlag", student.getHasNotLoginFlag());
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生班级号列表
     * @email 1538520381@qq.com
     * @date 2025/2/6 下午10:49
     */
    @Override
    public R getClassNumberList() {
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<Student>()
                .eq(Student::getIsDeleted, false);
        List<Student> studentList = super.list(lambdaQueryWrapper);
        Set<String> classNumberSet = new HashSet<>();
        for (Student student : studentList) {
            classNumberSet.add(student.getClassNumber());
        }
        return R.success().put("classNumberSet", classNumberSet);
    }

    @Override
    public Page<Student> queryPage(QueryPageWithStudentAdminStudentStatusRecordDto queryPageWithStudentAdminStudentStatusRecordDto) {
        Page<Student> pageInfo = new Page<>(queryPageWithStudentAdminStudentStatusRecordDto.getPage(), queryPageWithStudentAdminStudentStatusRecordDto.getPageSize());

        MPJLambdaWrapper<Student> lambdaQueryWrapper = new MPJLambdaWrapper<Student>()
                .selectAll(Student.class)
                .leftJoin(StudentAdminStudent.class, (wrapper) ->
                        wrapper
                                .eq(StudentAdminStudent::getStudentId, Student::getId)
                                .and(subWrapper -> subWrapper.isNull(StudentAdminStudent::getIsDeleted)
                                        .or()
                                        .eq(StudentAdminStudent::getIsDeleted, false)))
                .eq(Student::getIsDeleted, false)
                .orderByAsc(
                        "CASE WHEN class_number = '学硕1班' THEN 1 " +
                                "WHEN class_number = '学硕2班' THEN 2 " +
                                "WHEN class_number = '专硕1班' THEN 3 " +
                                "WHEN class_number = '专硕2班' THEN 4 " +
                                "WHEN class_number = '专硕3班' THEN 5 " +
                                "WHEN class_number = '博士班' THEN 6 " +
                                "ELSE 7 END"
                )
                // 对不在列表中的班级号按班级名排序
                .orderByAsc("CASE WHEN class_number NOT IN ('学硕1班', '学硕2班', '专硕1班', '专硕2班', '专硕3班', '博士班') THEN class_number END")
                .orderByDesc(StudentAdminStudent::getStudentAdminId)
                .orderByDesc(Student::getType)
                .orderByAsc(Student::getStudentNumber);

        if (queryPageWithStudentAdminStudentStatusRecordDto.getStudentNumber() != null) {
            lambdaQueryWrapper.like(Student::getStudentNumber, queryPageWithStudentAdminStudentStatusRecordDto.getStudentNumber());
        }

        if (queryPageWithStudentAdminStudentStatusRecordDto.getName() != null) {
            lambdaQueryWrapper.like(Student::getName, queryPageWithStudentAdminStudentStatusRecordDto.getName());
        }

        if (queryPageWithStudentAdminStudentStatusRecordDto.getClassNumber() != null) {
            lambdaQueryWrapper.eq(Student::getClassNumber, queryPageWithStudentAdminStudentStatusRecordDto.getClassNumber());
        }

        super.page(pageInfo, lambdaQueryWrapper);

        return pageInfo;
    }
}
