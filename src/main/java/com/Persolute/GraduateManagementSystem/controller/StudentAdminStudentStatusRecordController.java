package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecord.UpdateDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecord;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.studentAdminStudentStatusRecord.WithStudentAdminStudentStatusRecordDateVO;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private StudentAdminStudentStatusRecordDateService studentAdminStudentStatusRecordDateService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 更新学生管理员学生状态记录
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午1:52
     */
    @PutMapping("/update")
    public R update(@RequestBody UpdateDto updateDto) {
        if (updateDto.getStudentAdminId() == null) {
            throw new CustomerException("服务器异常");
        } else if (updateDto.getStudentId() == null) {
            throw new CustomerException("服务器异常");
        } else if (updateDto.getStudentAdminStudentStatusRecordDateId() == null) {
            throw new CustomerException("服务器异常");
        } else if (updateDto.getOnCampusFlag() == null) {
            throw new CustomerException("是否在校不能为空");
        } else if (!updateDto.getOnCampusFlag() && updateDto.getLeavingSchoolDetails() == null) {
            throw new CustomerException("学生若离校离校原因及去向不能为空");
        } else if (updateDto.getScientificResearchProgress() == null) {
            throw new CustomerException("科研进展情况不能为空");
        } else if (updateDto.getPersonalityTraits() == null) {
            throw new CustomerException("性格、优缺点不能为空");
        } else if (updateDto.getAbnormalIssues() == null) {
            throw new CustomerException("异常问题不能为空");
        }

        StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = new UpdateDto();
        studentAdminStudentStatusRecord.setStudentAdminId(updateDto.getStudentAdminId());
        studentAdminStudentStatusRecord.setStudentId(updateDto.getStudentId());
        studentAdminStudentStatusRecord.setStudentAdminStudentStatusRecordDateId(updateDto.getStudentAdminStudentStatusRecordDateId());
        studentAdminStudentStatusRecord.setOnCampusFlag(updateDto.getOnCampusFlag());
        studentAdminStudentStatusRecord.setLeavingSchoolDetails(updateDto.getLeavingSchoolDetails());
        studentAdminStudentStatusRecord.setScientificResearchProgress(updateDto.getScientificResearchProgress());
        studentAdminStudentStatusRecord.setPersonalityTraits(updateDto.getPersonalityTraits());
        studentAdminStudentStatusRecord.setAbnormalIssues(updateDto.getAbnormalIssues());

        return studentAdminStudentStatusRecordService.update(studentAdminStudentStatusRecord);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取学生管理员学生状态记录通过学生主键直到当前时间携带学生管理员学生状态记录日期
     * @email 1538520381@qq.com
     * @date 2025/1/20 下午6:20
     */
    @GetMapping("/getStudentAdminStudentStatusRecordByStudentIdToNowWithStudentAdminStudentStatusRecordDate/{studentId}")
    public R getStudentAdminStudentStatusRecordByStudentIdToNowWithStudentAdminStudentStatusRecordDate(@PathVariable Long studentId) {
        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = (List<StudentAdminStudentStatusRecordDate>) studentAdminStudentStatusRecordDateService.getToNowTime().get("studentAdminStudentStatusRecordDateList");

        List<WithStudentAdminStudentStatusRecordDateVO> withStudentAdminStudentStatusRecordDateVOList = studentAdminStudentStatusRecordDateList.stream().map((item) -> {
            WithStudentAdminStudentStatusRecordDateVO withStudentAdminStudentStatusRecordDateVO = new WithStudentAdminStudentStatusRecordDateVO();
            withStudentAdminStudentStatusRecordDateVO.setStudentAdminStudentStatusRecordDate(item);
            if (studentAdminStudentStatusRecordService.getByStudentIdAndStudentAdminStudentStatusRecordDateId(studentId, item.getId()).get("studentAdminStudentStatusRecord") != null) {
                StudentAdminStudentStatusRecord studentAdminStudentStatusRecord = (StudentAdminStudentStatusRecord) studentAdminStudentStatusRecordService.getByStudentIdAndStudentAdminStudentStatusRecordDateId(studentId, item.getId()).get("studentAdminStudentStatusRecord");
                BeanUtils.copyProperties(studentAdminStudentStatusRecord, withStudentAdminStudentStatusRecordDateVO);
            }
            return withStudentAdminStudentStatusRecordDateVO;
        }).collect(Collectors.toList());

        return R.success().put("withStudentAdminStudentStatusRecordDateVOList", withStudentAdminStudentStatusRecordDateVOList);
    }
}
