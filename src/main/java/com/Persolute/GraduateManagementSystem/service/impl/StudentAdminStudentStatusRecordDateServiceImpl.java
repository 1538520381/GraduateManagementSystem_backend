package com.Persolute.GraduateManagementSystem.service.impl;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.mapper.StudentAdminStudentStatusRecordDateMapper;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordDate ServiceImpl
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:02
 */
@Service
public class StudentAdminStudentStatusRecordDateServiceImpl extends ServiceImpl<StudentAdminStudentStatusRecordDateMapper, StudentAdminStudentStatusRecordDate> implements StudentAdminStudentStatusRecordDateService {
    /*
     * @author Persolute
     * @version 1.0
     * @description 新增学生管理员学生状态记录日期列表
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午3:32
     */
    @Override
    public R addList(List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDates) {
        if (!super.saveBatch(studentAdminStudentStatusRecordDates)) {
            throw new CustomerException("服务器异常");
        }

        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据现在时间获取
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午4:51
     */
    @Override
    public R getByNowTime() {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .le(StudentAdminStudentStatusRecordDate::getStartTime, new Date())
                .gt(StudentAdminStudentStatusRecordDate::getEndTime, new Date());
        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = super.getOne(lambdaQueryWrapper);
        return R.success().put("studentAdminStudentStatusRecordDate", studentAdminStudentStatusRecordDate);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取直到当前时间
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午5:57
     */
    @Override
    public R getToNowTime() {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .le(StudentAdminStudentStatusRecordDate::getStartTime, new Date());
        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = super.list(lambdaQueryWrapper);
        return R.success().put("studentAdminStudentStatusRecordDateList", studentAdminStudentStatusRecordDateList);
    }

    @Override
    public StudentAdminStudentStatusRecordDate getBySemesterAndWeek(String semester, String week) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecordDate::getSemester, semester)
                .eq(StudentAdminStudentStatusRecordDate::getWeek, week);
        return super.getOne(lambdaQueryWrapper);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:30
     */
    @Override
    public R getList() {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false);
        return R.success().put("studentAdminStudentStatusRecordDateList", super.list(lambdaQueryWrapper));
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页获取
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:49
     */
    @Override
    public Page<StudentAdminStudentStatusRecordDate> getPage(Integer page, Integer pageSize) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .orderByDesc(StudentAdminStudentStatusRecordDate::getStartTime);
        Page<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDatePage = new Page<>(page, pageSize);
        super.page(studentAdminStudentStatusRecordDatePage, lambdaQueryWrapper);
        return studentAdminStudentStatusRecordDatePage;
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:50
     */
    @Override
    public R deleteById(Long id) {
        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = new StudentAdminStudentStatusRecordDate();
        studentAdminStudentStatusRecordDate.setId(id);
        studentAdminStudentStatusRecordDate.setIsDeleted(true);
        super.updateById(studentAdminStudentStatusRecordDate);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增
     * @email 1538520381@qq.com
     * @date 2025/3/7 上午11:12
     */
    @Override
    public R add(StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper1 = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .and(wrapper -> wrapper
                        .le(StudentAdminStudentStatusRecordDate::getStartTime, studentAdminStudentStatusRecordDate.getStartTime())
                        .gt(StudentAdminStudentStatusRecordDate::getEndTime, studentAdminStudentStatusRecordDate.getStartTime())
                        .or()
                        .lt(StudentAdminStudentStatusRecordDate::getStartTime, studentAdminStudentStatusRecordDate.getEndTime())
                        .ge(StudentAdminStudentStatusRecordDate::getEndTime, studentAdminStudentStatusRecordDate.getEndTime()));
        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = super.list(lambdaQueryWrapper1);
        if (!studentAdminStudentStatusRecordDateList.isEmpty()) {
            throw new CustomerException("时间与其他存在重叠");
        }

        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper2 = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecordDate::getSemester, studentAdminStudentStatusRecordDate.getSemester())
                .eq(StudentAdminStudentStatusRecordDate::getWeek, studentAdminStudentStatusRecordDate.getWeek());
        studentAdminStudentStatusRecordDateList = super.list(lambdaQueryWrapper2);
        if (!studentAdminStudentStatusRecordDateList.isEmpty()) {
            throw new CustomerException("学期+周不能重复");
        }

        super.save(studentAdminStudentStatusRecordDate);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表
     * @email 1538520381@qq.com
     * @date 2025/3/7 上午11:10
     */
    @Override
    public List<StudentAdminStudentStatusRecordDate> getStudentAdminStudentStatusRecordDateList() {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false);
        return super.list(lambdaQueryWrapper);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增返回id
     * @email 1538520381@qq.com
     * @date 2025/3/7 上午11:10
     */
    @Override
    public Long addReturnId(StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate) {
        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper1 = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .and(wrapper -> wrapper
                        .le(StudentAdminStudentStatusRecordDate::getStartTime, studentAdminStudentStatusRecordDate.getStartTime())
                        .gt(StudentAdminStudentStatusRecordDate::getEndTime, studentAdminStudentStatusRecordDate.getStartTime())
                        .or()
                        .lt(StudentAdminStudentStatusRecordDate::getStartTime, studentAdminStudentStatusRecordDate.getEndTime())
                        .ge(StudentAdminStudentStatusRecordDate::getEndTime, studentAdminStudentStatusRecordDate.getEndTime()));
        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = super.list(lambdaQueryWrapper1);
        if (!studentAdminStudentStatusRecordDateList.isEmpty()) {
            throw new CustomerException("时间与其他存在重叠");
        }

        LambdaQueryWrapper<StudentAdminStudentStatusRecordDate> lambdaQueryWrapper2 = new LambdaQueryWrapper<StudentAdminStudentStatusRecordDate>()
                .eq(StudentAdminStudentStatusRecordDate::getIsDeleted, false)
                .eq(StudentAdminStudentStatusRecordDate::getSemester, studentAdminStudentStatusRecordDate.getSemester())
                .eq(StudentAdminStudentStatusRecordDate::getWeek, studentAdminStudentStatusRecordDate.getWeek());
        studentAdminStudentStatusRecordDateList = super.list(lambdaQueryWrapper2);
        if (!studentAdminStudentStatusRecordDateList.isEmpty()) {
            throw new CustomerException("学期+周不能重复");
        }

        super.save(studentAdminStudentStatusRecordDate);
        return studentAdminStudentStatusRecordDate.getId();
    }
}
