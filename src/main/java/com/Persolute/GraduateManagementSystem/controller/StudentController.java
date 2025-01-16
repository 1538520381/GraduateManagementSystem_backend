package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.StudentAddListDto;
import com.Persolute.GraduateManagementSystem.entity.dto.StudentQueryListDto;
import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
