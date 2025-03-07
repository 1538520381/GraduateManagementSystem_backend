package com.Persolute.GraduateManagementSystem.filter;

import com.Persolute.GraduateManagementSystem.entity.result.R;
import com.Persolute.GraduateManagementSystem.exception.CustomerException;
import com.Persolute.GraduateManagementSystem.util.JWTUtil;
import com.Persolute.GraduateManagementSystem.util.WebUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Persolute
 * @version 1.0
 * @description token过滤器
 * @email 1538520381@qq.com
 * @date 2025/01/15 13:36
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private static String[] whiteList = {
            "/admin/register",
            "/admin/login",
            "/student/adminLogin",
            "/student/login",
            "/document/download/**",
            "/studentAdminStudentStatusRecordDate/addByStartTimeAndCycleLengthAndCycleNumber"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");

        if (check(JwtAuthenticationTokenFilter.whiteList, httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (!StringUtils.hasText(token)) {
            WebUtil.renderString(httpServletResponse, JSON.toJSONString(R.error("用户未登录")));
            return;
        }

        String userId;
        try {
            Claims claims = JWTUtil.paresJWT(token);
            userId = claims.getSubject();
        } catch (
                Exception e) {
            WebUtil.renderString(httpServletResponse, JSON.toJSONString(R.error("非法token")));
            return;
        }

        Object user = redisTemplate.opsForValue().get("login_" + userId);
        if (user == null) {
            WebUtil.renderString(httpServletResponse, JSON.toJSONString(R.error("用户未登录")));
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
