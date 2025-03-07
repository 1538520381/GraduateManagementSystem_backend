package com.Persolute.GraduateManagementSystem.util;

import com.Persolute.GraduateManagementSystem.exception.CustomerException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Persolute
 * @version 1.0
 * @description Web Util
 * @email 1538520381@qq.com
 * @date 2025/02/05 15:35
 */
public class WebUtil {
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException(e.getMessage());
        }
        return null;
    }
}
