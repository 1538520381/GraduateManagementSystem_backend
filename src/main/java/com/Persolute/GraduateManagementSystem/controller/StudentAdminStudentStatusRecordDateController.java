package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate.AddByStartTimeAndCycleLengthAndCycleNumberDto;
import com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate.AddDto;
import com.Persolute.GraduateManagementSystem.entity.dto.studentAdminStudentStatusRecordDate.GetPageDto;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordDate;
import com.Persolute.GraduateManagementSystem.entity.po.StudentAdminStudentStatusRecordName;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.StudentAdminStudentStatusRecordDate.GetPageVO;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordDateService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordNameService;
import com.Persolute.GraduateManagementSystem.service.StudentAdminStudentStatusRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private StudentAdminStudentStatusRecordService studentAdminStudentStatusRecordService;
    @Autowired
    private StudentAdminStudentStatusRecordNameService studentAdminStudentStatusRecordNameService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增根据开始时间、周期长度、周期数
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午3:34
     */
    @PostMapping("/addByStartTimeAndCycleLengthAndCycleNumber")
    public R addByStartTimeAndCycleLengthAndCycleNumber(@RequestBody AddByStartTimeAndCycleLengthAndCycleNumberDto addByStartTimeAndCycleLengthAndCycleNumberDto) {
        if (addByStartTimeAndCycleLengthAndCycleNumberDto.getStartTime() == null) {
            throw new CustomerException("开始时间不能为空");
        } else if (addByStartTimeAndCycleLengthAndCycleNumberDto.getCycleLength() == null) {
            throw new CustomerException("周期长度不能为空");
        } else if (addByStartTimeAndCycleLengthAndCycleNumberDto.getCycleNumber() == null) {
            throw new CustomerException("周期数不能为空");
        } else if (addByStartTimeAndCycleLengthAndCycleNumberDto.getSemester() == null) {
            throw new CustomerException("学期不能为空");
        }

        List<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDateList = new ArrayList<>();

        Date startTime = addByStartTimeAndCycleLengthAndCycleNumberDto.getStartTime();
        int cycleLength = addByStartTimeAndCycleLengthAndCycleNumberDto.getCycleLength();
        int cycleNumber = addByStartTimeAndCycleLengthAndCycleNumberDto.getCycleNumber();
        for (int i = 0; i < cycleNumber; i++) {
            StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = new StudentAdminStudentStatusRecordDate();
            studentAdminStudentStatusRecordDate.setStartTime(startTime);
            startTime = new Date(startTime.getTime() + cycleLength);
            studentAdminStudentStatusRecordDate.setEndTime(startTime);
            studentAdminStudentStatusRecordDate.setSemester(addByStartTimeAndCycleLengthAndCycleNumberDto.getSemester());
            studentAdminStudentStatusRecordDate.setWeek("第" + (i + 1) + "周");
            studentAdminStudentStatusRecordDateList.add(studentAdminStudentStatusRecordDate);
        }

        return studentAdminStudentStatusRecordDateService.addList(studentAdminStudentStatusRecordDateList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据现在时间获取携带学生管理员学生状态记录通过学生id
     * @email 1538520381@qq.com
     * @date 2025/1/18 下午5:18
     */
    @GetMapping("/getByNowTimeWithStudentAdminStudentStatusRecordByStudentId/{studentId}")
    public R getByNowTimeWithStudentAdminStudentStatusRecordByStudentId(@PathVariable Long studentId) {
        R r1 = studentAdminStudentStatusRecordDateService.getByNowTime();
        if (r1.get("studentAdminStudentStatusRecordDate") == null) {
            return R.success();
        }

        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = (StudentAdminStudentStatusRecordDate) r1.get("studentAdminStudentStatusRecordDate");
        R r2 = studentAdminStudentStatusRecordService.getByStudentIdAndStudentAdminStudentStatusRecordDateId(studentId, studentAdminStudentStatusRecordDate.getId());
        r2.put("studentAdminStudentStatusRecordDate", studentAdminStudentStatusRecordDate);

        List<StudentAdminStudentStatusRecordName> studentAdminStudentStatusRecordNameList = studentAdminStudentStatusRecordNameService.getListByStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDate.getId());
        return r2.put("studentAdminStudentStatusRecordNameList", studentAdminStudentStatusRecordNameList);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 获取列表
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:27
     */
    @GetMapping("/getList")
    public R getList() {
        return studentAdminStudentStatusRecordDateService.getList();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 分页获取
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:27
     */
    @GetMapping("/getPage")
    public R getPage(GetPageDto getPageDto) {
        Page<StudentAdminStudentStatusRecordDate> studentAdminStudentStatusRecordDatePage = studentAdminStudentStatusRecordDateService.getPage(getPageDto.getPage(), getPageDto.getPageSize());
        List<GetPageVO> getPageVOList = studentAdminStudentStatusRecordDatePage.getRecords().stream().map((item) -> {
            GetPageVO getPageVO = new GetPageVO();
            BeanUtils.copyProperties(item, getPageVO);
            List<StudentAdminStudentStatusRecordName> studentAdminStudentStatusRecordNameList = studentAdminStudentStatusRecordNameService.getListByStudentAdminStudentStatusRecordDateId(item.getId());
            getPageVO.setProblems(studentAdminStudentStatusRecordNameList);
            return getPageVO;
        }).collect(Collectors.toList());
        Page<GetPageVO> pageInfo = new Page<>();
        BeanUtils.copyProperties(studentAdminStudentStatusRecordDatePage, pageInfo);
        pageInfo.setRecords(getPageVOList);
        return R.success().put("pageInfo", pageInfo);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据id删除
     * @email 1538520381@qq.com
     * @date 2025/3/1 下午2:51
     */
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable Long id) {
        return studentAdminStudentStatusRecordDateService.deleteById(id);
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增
     * @email 1538520381@qq.com
     * @date 2025/3/7 上午11:16
     */
    @PostMapping("/add")
    public R add(@RequestBody AddDto addDto) {
        StudentAdminStudentStatusRecordDate studentAdminStudentStatusRecordDate = new StudentAdminStudentStatusRecordDate();
        BeanUtils.copyProperties(addDto, studentAdminStudentStatusRecordDate);

        if (studentAdminStudentStatusRecordDate.getSemester() == null) {
            throw new CustomerException("学期不能为空");
        } else if (studentAdminStudentStatusRecordDate.getWeek() == null) {
            throw new CustomerException("周不能为空");
        } else if (studentAdminStudentStatusRecordDate.getStartTime() == null) {
            throw new CustomerException("开始时间不能为空");
        } else if (studentAdminStudentStatusRecordDate.getEndTime() == null) {
            throw new CustomerException("结束时间不能为空");
        } else if (studentAdminStudentStatusRecordDate.getStartTime().compareTo(studentAdminStudentStatusRecordDate.getEndTime()) >= 0) {
            throw new CustomerException("开始时间要在结束时间前");
        }

        Long studentAdminStudentStatusRecordDateId = studentAdminStudentStatusRecordDateService.addReturnId(studentAdminStudentStatusRecordDate);
        List<StudentAdminStudentStatusRecordName> studentAdminStudentStatusRecordNameList = new ArrayList<>();
        for (int i = 0; i < addDto.getProblems().size(); i++) {
            StudentAdminStudentStatusRecordName studentAdminStudentStatusRecordName = new StudentAdminStudentStatusRecordName();
            studentAdminStudentStatusRecordName.setStudentAdminStudentStatusRecordDateId(studentAdminStudentStatusRecordDateId);
            studentAdminStudentStatusRecordName.setSort(i + 1);
            studentAdminStudentStatusRecordName.setStem(addDto.getProblems().get(i));
            studentAdminStudentStatusRecordNameList.add(studentAdminStudentStatusRecordName);
        }
        studentAdminStudentStatusRecordNameService.saveBatch(studentAdminStudentStatusRecordNameList);
        return R.success();
    }

    @GetMapping("/getById/{id}")
    public R getById(@PathVariable Long id) {
        return R.success().put("studentAdminStudentStatusRecordDate", studentAdminStudentStatusRecordDateService.getById(id));
    }
}
