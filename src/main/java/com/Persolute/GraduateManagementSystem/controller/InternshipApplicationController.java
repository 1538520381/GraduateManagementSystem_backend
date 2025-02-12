package com.Persolute.GraduateManagementSystem.controller;

import com.Persolute.GraduateManagementSystem.entity.po.InternshipApplication;
import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.entity.vo.InternshipApplication.ListByStudentIdWithDocumentSortByCreateTimeVO;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.service.DocumentService;
import com.Persolute.GraduateManagementSystem.service.InternshipApplicationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Persolute
 * @version 1.0
 * @description InternshipApplication Controller
 * @email 1538520381@qq.com
 * @date 2025/02/12 11:15
 */
@RestController
@RequestMapping("/internshipApplication")
public class InternshipApplicationController {
    @Autowired
    private InternshipApplicationService internshipApplicationService;

    @Autowired
    private DocumentService documentService;

    /*
     * @author Persolute
     * @version 1.0
     * @description 新增
     * @email 1538520381@qq.com
     * @date 2025/2/12 下午1:15
     */
    @PostMapping("/add")
    public R add(@RequestBody InternshipApplication internshipApplication) {
        if (internshipApplication.getStudentId() == null) {
            throw new CustomerException();
        } else if (internshipApplication.getInternshipStartTime() == null) {
            throw new CustomerException("实习开始时间不能为空");
        } else if (internshipApplication.getInternshipEndTime() == null) {
            throw new CustomerException("实习结束时间不能为空");
        } else if (internshipApplication.getInternshipUnit() == null) {
            throw new CustomerException("实习单位名称不能为空");
        } else if (internshipApplication.getOffCampusHousingFlag() == null) {
            throw new CustomerException("是否校外住宿不能为空");
        } else if (internshipApplication.getInternshipApplicationFormDocumentId() == null) {
            throw new CustomerException("实习申请单不能为空");
        } else if (internshipApplication.getParentalNotificationLetterDocumentId() == null) {
            throw new CustomerException("家长知情书不能为空");
        }
        internshipApplication.setStatus(0);
        internshipApplicationService.save(internshipApplication);
        return R.success();
    }

    /*
     * @author Persolute
     * @version 1.0
     * @description 根据学生主键列表携带文件
     * @email 1538520381@qq.com
     * @date 2025/2/12 下午1:49
     */
    @GetMapping("/listByStudentIdWithDocumentSortByCreateTime/{studentId}")
    public R listByStudentIdWithDocumentSortByCreateTime(@PathVariable Long studentId) {
        List<InternshipApplication> internshipApplicationList = internshipApplicationService.listByStudentIdWithDocumentSortByCreateTime(studentId);
        List<ListByStudentIdWithDocumentSortByCreateTimeVO> listByStudentIdWithDocumentVOListSortByCreateTime = internshipApplicationList.stream().map((item) -> {
            ListByStudentIdWithDocumentSortByCreateTimeVO listByStudentIdWithDocumentSortByCreateTimeVO = new ListByStudentIdWithDocumentSortByCreateTimeVO();
            BeanUtils.copyProperties(item, listByStudentIdWithDocumentSortByCreateTimeVO);
            listByStudentIdWithDocumentSortByCreateTimeVO.setInternshipApplicationFormDocument(documentService.getById(item.getInternshipApplicationFormDocumentId()));
            listByStudentIdWithDocumentSortByCreateTimeVO.setParentalNotificationLetterDocument(documentService.getById(item.getParentalNotificationLetterDocumentId()));
            return listByStudentIdWithDocumentSortByCreateTimeVO;
        }).collect(Collectors.toList());
        return R.success().put("listByStudentIdWithDocumentVOList", listByStudentIdWithDocumentVOListSortByCreateTime);
    }
}
