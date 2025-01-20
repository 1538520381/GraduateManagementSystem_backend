package com.Persolute.GraduateManagementSystem.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjecthandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getValue("createTime") != null) {
            metaObject.setValue("createTime", LocalDateTime.now());
        }

        if (metaObject.getValue("updateTime") != null) {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.getValue("updateTime") != null) {
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
    }
}
