package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.StudentAdminStudentStatusRecordUpdateDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecord Controller
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:11
 */
@RestController
@RequestMapping("/studentAdminStudentStatusRecord")
public class StudentAdminStudentStatusRecordController {
    @Autowired
    private StudentAdminStudentStatusRecordService studentAdminStudentStatusRecordService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新学生管理员学生状态记录
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午1:52
     */
    @PutMapping("/update")
    public R update(@RequestBody StudentAdminStudentStatusRecordUpdateDto studentAdminStudentStatusRecordUpdateDto) {
        if (studentAdminStudentStatusRecordUpdateDto.getStudentAdminId() == null) {
            throw new CustomerException("服务器异常");
        } else if (studentAdminStudentStatusRecordUpdateDto.getStudentId() == null) {
            throw new CustomerException("服务器异常");
        } else if (studentAdminStudentStatusRecordUpdateDto.getStudentAdminStudentStatusRecordDateId() == null) {
            throw new CustomerException("服务器异常");
        } else if (studentAdminStudentStatusRecordUpdateDto.getOnCampusFlag() == null) {
            throw new CustomerException("是否在校不能为空");
        } else if (!studentAdminStudentStatusRecordUpdateDto.getOnCampusFlag() && studentAdminStudentStatusRecordUpdateDto.getLeavingSchoolDetails() == null) {
            throw new CustomerException("学生若离校离校原因及去向不能为空");
        } else if (studentAdminStudentStatusRecordUpdateDto.getScientificResearchProgress() == null) {
            throw new CustomerException("科研进展情况不能为空");
        } else if (studentAdminStudentStatusRecordUpdateDto.getPersonalityTraits() == null) {
            throw new CustomerException("性格、优缺点不能为空");
        } else if (studentAdminStudentStatusRecordUpdateDto.getAbnormalIssues() == null) {
            throw new CustomerException("异常问题不能为空");
        }

        StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = new StudentAdminStudentStatusRecordUpdateDto();
        studentAdminStudentStatusRecord.setStudentAdminId(studentAdminStudentStatusRecordUpdateDto.getStudentAdminId());
        studentAdminStudentStatusRecord.setStudentId(studentAdminStudentStatusRecordUpdateDto.getStudentId());
        studentAdminStudentStatusRecord.setStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordUpdateDto.getStudentAdminStudentStatusRecordDateId());
        studentAdminStudentStatusRecord.setOnCampusFlag(studentAdminStudentStatusRecordUpdateDto.getOnCampusFlag());
        studentAdminStudentStatusRecord.setLeavingSchoolDetails(studentAdminStudentStatusRecordUpdateDto.getLeavingSchoolDetails());
        studentAdminStudentStatusRecord.setScientificResearchProgress(studentAdminStudentStatusRecordUpdateDto.getScientificResearchProgress());
        studentAdminStudentStatusRecord.setPersonalityTraits(studentAdminStudentStatusRecordUpdateDto.getPersonalityTraits());
        studentAdminStudentStatusRecord.setAbnormalIssues(studentAdminStudentStatusRecordUpdateDto.getAbnormalIssues());

        return studentAdminStudentStatusRecordService.update(studentAdminStudentStatusRecord);
    }
}
