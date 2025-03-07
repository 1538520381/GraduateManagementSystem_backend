package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordName;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Persolute
 * @version 1.0
 * @description StudentAdminStudentStatusRecordName Controlle
 * @email 1538520381@qq.com
 * @date 2025/03/06 15:35
 */
@RestController
@RequestMapping("/studentAdminStudentStatusRecordName")
public class StudentAdminStudentStatusRecordNameController {
    @Autowired
    private StudentAdminStudentStatusRecordNameService studentAdminStudentStatusRecordNameService;
    @Autowired
    private StudentAdminStudentStatusRecordDateService studentAdminStudentStatusRecordDateService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表根据studentAdminStudentStatusrRecordDateId
     * @email 1538520381@qq.com
     * @date 2025/3/6 下午3:41
     */
    @GetMapping("/getByStudentAdminStudentStatusrRecordDateId/{studentAdminStudentStatusRecordDateId}")
    public R getByStudentAdminStudentStatusRecordDateId(@PathVariable Long studentAdminStudentStatusRecordDateId) {
        List<StudentAdminStudentStatusRecordName> studentAdminStudentStatusRecordNameList = studentAdminStudentStatusRecordNameService.getListByStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDateId);
        return R.success().put("studentAdminStudentStatusRecordNameList", studentAdminStudentStatusRecordNameList);
    }

    @GetMapping("/addWithNowStudentAdminStudentStatusRecordDate")
    public R addWithNowStudentAdminStudentStatusRecordDate() {
        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = studentAdminStudentStatusRecordDateService.getStudentAdminStudentStatusRecordDateList();
        List<StudentAdminStudentStatusRecordName> studentAdminStudentStatusRecordNameList = new ArrayList<>();
        for (StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate : studentAdminStudentStatusRecordDateList) {
            StudentAdminStudentStatusRecordName studentAdminStudentStatusRecordName1 = new StudentAdminStudentStatusRecordName();
            studentAdminStudentStatusRecordName1.setStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDate.getId());
            studentAdminStudentStatusRecordName1.setSort(1);
            studentAdminStudentStatusRecordName1.setStem("科研进展情况");

            StudentAdminStudentStatusRecordName studentAdminStudentStatusRecordName2 = new StudentAdminStudentStatusRecordName();
            studentAdminStudentStatusRecordName2.setStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDate.getId());
            studentAdminStudentStatusRecordName2.setSort(2);
            studentAdminStudentStatusRecordName2.setStem("性格、优缺点");

            StudentAdminStudentStatusRecordName studentAdminStudentStatusRecordName3 = new StudentAdminStudentStatusRecordName();
            studentAdminStudentStatusRecordName3.setStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDate.getId());
            studentAdminStudentStatusRecordName3.setSort(3);
            studentAdminStudentStatusRecordName3.setStem("需要特别关注的问题");

            studentAdminStudentStatusRecordNameList.add(studentAdminStudentStatusRecordName1);
            studentAdminStudentStatusRecordNameList.add(studentAdminStudentStatusRecordName2);
            studentAdminStudentStatusRecordNameList.add(studentAdminStudentStatusRecordName3);
        }
        studentAdminStudentStatusRecordNameService.saveBatch(studentAdminStudentStatusRecordNameList);
        return R.success();
    }
}
