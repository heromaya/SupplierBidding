package com.laodyu.web.util;

import com.laodyu.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/6
 * @Version 1.0
 **/
public class SessionUtil {
    public static void setSession(User user){
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("user",user);
    }
    public static User getSession(){
        Subject subject = SecurityUtils.getSubject();
        return (User)subject.getSession().getAttribute("user");
    }
}
