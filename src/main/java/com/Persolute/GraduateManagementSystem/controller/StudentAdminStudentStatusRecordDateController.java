package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.StudentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordDate Controller
 * @email 1538520381@qq.com
 * @date 2025/01/18 15:03
 */
@RestController
@RequestMapping("/studentAdminStudentStatusRecordDate")
public class StudentAdminStudentStatusRecordDateController {
    @Autowired
    private StudentAdminStudentStatusRecordDateService studentAdminStudentStatusRecordDateService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增根据开始时间、周期长度、周期数
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午3:34
     */
    @PostMapping("/addByStartTimeAndCycleLengthAndCycleNumber")
    public R addByStartTimeAndCycleLengthAndCycleNumber(@RequestBody StudentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto) {
        if (studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getStartTime() == null) {
            throw new CustomerException("开始时间不能为空");
        } else if (studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getCycleLength() == null) {
            throw new CustomerException("周期长度不能为空");
        } else if (studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getCycleNumber() == null) {
            throw new CustomerException("周期数不能为空");
        }

        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = new ArrayList<>();

        Date startTime = studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getStartTime();
        int cycleLength = studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getCycleLength();
        int cycleNumber = studentAdminStudentStatusRecordDateAddByStartTimeAndCycleLengthAndCycleNumberDto.getCycleNumber();
        for (int i = 0; i < cycleNumber; i++) {
            StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = new StudentAdminStudentStatusRecordDate();
            studentAdminStudentStatusRecordDate.setStartTime(startTime);
            startTime = new Date(startTime.getTime() + cycleLength);
            studentAdminStudentStatusRecordDate.setEndTime(startTime);
            studentAdminStudentStatusRecordDateList.add(studentAdminStudentStatusRecordDate);
        }

        return studentAdminStudentStatusRecordDateService.addList(studentAdminStudentStatusRecordDateList);
    }
}
