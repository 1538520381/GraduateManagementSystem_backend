package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudent.ChoiceTeamMemberDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudent Controller
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:49
 */
@RestController
@RequestMapping("/studentAdminStudent")
public class StudentAdminStudentController {
    @Autowired
    private StudentAdminStudentService studentAdminStudentService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 选择组员
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午1:55
     */
    @PostMapping("/choiceTeamMember")
    public R choiceTeamMember(@RequestBody ChoiceTeamMemberDto choiceTeamMemberDto) {
        if (choiceTeamMemberDto.getStudentAdminId() == null) {
            throw new CustomerException("服务器异常");
        } else if (choiceTeamMemberDto.getStudentId() == null) {
            throw new CustomerException("服务器异常");
        }
        StudentAdminStudent studentAdminStudent = new StudentAdminStudent();
        studentAdminStudent.setStudentAdminId(choiceTeamMemberDto.getStudentAdminId());
        studentAdminStudent.setStudentId(choiceTeamMemberDto.getStudentId());

        return studentAdminStudentService.addStudentAdminStudent(choiceTeamMemberDto);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 删除组员根据学生id
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午1:55
     */
    @DeleteMapping("/deleteTeamMemberByStudentId/{studentId}")
    public R deleteTeamMemberByStudentId(@PathVariable Long studentId) {
        if (studentId == null) {
            throw new CustomerException("服务器异常");
        }

        return studentAdminStudentService.deleteByStudentId(studentId);
    }
}
