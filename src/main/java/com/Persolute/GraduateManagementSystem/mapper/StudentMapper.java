package com.Persolute.GraduateManagementSystem.mapper;

import com.Persolute.GraduateManagementSystem.entity.po.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Persolute
 * @version 1.0
 * @description Student Mapper
 * @email 1538520381@qq.com
 * @date 2025/01/15 12:41
 */
@Mapper
public interface StudentMapper extends MPJBaseMapper<Student> {
}
