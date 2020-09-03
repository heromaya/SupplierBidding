package com.laodyu.web.config.shiro;


import com.laodyu.commons.resp.RespBean;
import com.laodyu.service.MenuService;
import com.laodyu.web.util.SpringContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Set;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/30
 * @Version 1.0
 **/
public class URLPathMatchFilter extends PathMatchingFilter {
    @Autowired
    MenuService menuService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception  {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //放行 options 请求
        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
            return true ;
        }

        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
           //未登录
            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(401);
            RespBean respBean = RespBean.error("你还未登录！请登录！");
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(respBean));
            out.flush();
            out.close();
            return false;
        }

        if (menuService == null) {
            menuService = SpringContextUtils.getContext().getBean(MenuService.class);
        }

        String requestAPI = getPathWithinApplication(request);

        //判断访问接口是否需要过滤（数据库中是否有对应信息）
        boolean needFilter = menuService.needFilter(requestAPI);
        if (!needFilter) {
            //不需要过滤
            return true;
        } else {
            //判断当前用户是否有相应权限
            boolean hasPermission = false;
            String code = subject.getPrincipal().toString();
            Set<String> urlList = menuService.getUrlSet(code);
            for (String url : urlList) {
                if (url.indexOf(requestAPI) != -1) {
                    hasPermission = true;
                    break;
                }
            }
            if(hasPermission){
                return true ;
            }else {
                //没有相应的权限
                resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
                resp.setHeader("Access-Control-Allow-Credentials", "true");
                resp.setContentType("application/json; charset=utf-8");
                resp.setCharacterEncoding("UTF-8");
                resp.setStatus(403);
                RespBean respBean = RespBean.error("对不起，您没有访问"+requestAPI+"的权限！");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();
                return false;
            }

        }

    }
}
