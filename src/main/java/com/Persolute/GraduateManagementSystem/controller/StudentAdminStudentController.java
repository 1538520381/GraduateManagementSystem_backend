package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.StudentAdminStudentChoiceTeamMemberDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudent;
import com.Persolute.GraduateManagementSystem.entity.result.R;
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

    @PostMapping("/choiceTeamMember")
    public R choiceTeamMember(@RequestBody StudentAdminStudentChoiceTeamMemberDto studentAdminStudentChoiceTeamMemberDto) {
        if (studentAdminStudentChoiceTeamMemberDto.getStudentAdminId() == null) {
            return R.error();
        } else if (studentAdminStudentChoiceTeamMemberDto.getStudentId() == null) {
            return R.error();
        }
        StudentAdminStudent studentAdminStudent = new StudentAdminStudent();
        studentAdminStudent.setStudentAdminId(studentAdminStudentChoiceTeamMemberDto.getStudentAdminId());
        studentAdminStudent.setStudentId(studentAdminStudentChoiceTeamMemberDto.getStudentId());

        return studentAdminStudentService.addStudentAdminStudent(studentAdminStudentChoiceTeamMemberDto);
    }
}
