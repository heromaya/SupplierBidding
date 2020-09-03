package com.laodyu.web.controller.sys;

import com.laodyu.commons.constant.ENProcessKey;
import com.laodyu.commons.constant.ENUserState;
import com.laodyu.entity.Role;
import com.laodyu.entity.Supplier;
import com.laodyu.entity.User;
import com.laodyu.commons.exception.BizException;
import com.laodyu.service.RoleService;
import com.laodyu.service.SupplierService;
import com.laodyu.service.UserRoleService;
import com.laodyu.service.UserService;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.web.activiti.service.ActivitiService;
import com.laodyu.web.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ActivitiService activitiService;
    @Autowired
    RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping(value = "/login")
    public String login(){

        Subject subject = SecurityUtils.getSubject();
        //判断当前用户是否有使用rememberMe
        if (subject.isRemembered()){
            return "main";
            //判断当前用户是否登录
        }else if(subject.isAuthenticated()){
            return "main";
        }
        return "login";
    }


    @PostMapping("/login")
    public RespBean login(@RequestParam  Map<String, String> params) {

        String code = HtmlUtils.htmlEscape(params.get("code"));
        if(StringUtils.isEmpty(code)){
            throw new BizException("用户名不能为空！");
        }
        if(StringUtils.isEmpty(params.get("password"))){
            throw new BizException("密码不能为空！");
        }
        Subject subject = SecurityUtils.getSubject();
        boolean rememberMe = params.get("checked").equals("true")?true:false;
        UsernamePasswordToken token = new UsernamePasswordToken(code, params.get("password"),rememberMe);
        User user = userService.findByCode(code);
        try {
            if (user != null) {
                subject.login(token);
                //更新登录时间
                user.setLastlogintime(new Timestamp(System.currentTimeMillis()));
                //更新最后的操作时间
                user.setUpdatedon(new Timestamp(System.currentTimeMillis()));
                userService.updateUser(user);
                SessionUtil.setSession(user);
                user.setPassword(null);
                user.setSalt(null);
                List<Role> roleList = roleService.findRoleListByUserid(user.getUrid());
                user.setRoleList(roleList);
                logger.info(user.getCode()+" 登录系统！");
                return RespBean.ok("登录成功！", user);
            }
            return RespBean.error("用户名或密码错误！");
        } catch (IncorrectCredentialsException e) {
            return RespBean.error("用户名或密码错误！");
        } catch (UnknownAccountException e) {
            return RespBean.error("账号不存在!");
        } catch (LockedAccountException e) {
            return RespBean.error("账号已冻结，请联系管理员");
        } catch (DisabledAccountException e) {
            return RespBean.error("账号未激活，还在审批中");
        }
    }

    /***
     * 供应商注册
     * 1.供应商提交注册信息。
     * 2.审核人员审批供应商信息。
     * 3.审核通过后方可进入系统
     * @param params
     * @return
     */
    @PostMapping("/register")
    public RespBean register(@RequestParam Map<String, String> params) {
        String code = HtmlUtils.htmlEscape(params.get("userid"));

        User user = null;
        user = userService.findByCode(code);
        //检查账号
        if (user != null) {
            String message = "该用户名已经注册了！";
            return RespBean.error(message);
        }
        Supplier supplier = null;
        //检查邮箱
        supplier = supplierService.findByEmail(params.get("email"));
        if (supplier != null) {
            String message = "该邮箱已经注册了！";
            return RespBean.error(message);
        }
        //检查手机号
        supplier = supplierService.findByTelephone(params.get("telephone"));
        if (supplier != null) {
            String message = "该手机号已经注册了";
            return RespBean.error(message);
        }

        //生成盐 默认长度 16位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //设置 hash 算法迭代次数
        int times = 2;
        //得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", params.get("password"), salt, times).toString();
        try {
            User currentUser = new User();
            currentUser.setCode(code);
            //启动流程
            String processInstanceId = activitiService.stratProcess(ENProcessKey.SupplierRegister.getValue(), code);
            AddInfo(code,encodedPassword,salt,params,currentUser,processInstanceId);
            return RespBean.ok("提交注册信息成功!，待审核通过后可登陆系统！");
        } catch (Exception e) {
            return RespBean.error("系统出现异常，注册失败!");
        }

    }
    /***
     *
     * @param code
     * @param encodedPassword
     * @param salt
     * @param params
     */
    public void AddInfo(String code,String encodedPassword ,String salt,Map<String,String> params,User currentUser,String processInstanceId) {
        //存储用户信息，包括 salt 与 hash后的密码
        userService.AddUser(code, encodedPassword, salt, ENUserState.DISACTIVE.getValue(),currentUser);
        //存储供应商信息
        supplierService.AddSupplier(params, encodedPassword, salt,processInstanceId);
    }

    /***
     * 注销登录
     * @return
     */
    @GetMapping("/logout")
    public RespBean logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RespBean.ok("注销成功！");
    }

    @PostMapping("/changepassword")
    public RespBean changePassword(@RequestParam Map<String, String> params){
        String code = params.get("code");
        String oldpassword = params.get("oldPassword");
        String newpassword = params.get("newPassword");
        String newpasswordAgain = params.get("newPasswordAgain");
        if(StringUtils.isEmpty(oldpassword) || StringUtils.isEmpty(newpasswordAgain)|| StringUtils.isEmpty(newpassword) || StringUtils.isEmpty(code)){
            throw new BizException("请输入完整数据!");
        }
        if(!newpasswordAgain.equals(newpassword)){
            throw new BizException("两次输入的新密码不一致！");
        }
        User user = userService.findByCode(code);
        //盐值
        String salt = user.getSalt();
        //当前登录的加密密码
        String password = user.getPassword();
        //前端传过来的旧密码加盐加密
        String encodedOldPassword = new SimpleHash("md5",oldpassword, salt, 2).toString();
        //前端传过来的新密码加盐加密
        String encodedNewPassword = new SimpleHash("md5",newpassword, salt, 2).toString();
        //判断当前登录的密码和前端传过来的加盐加密后的密码是否一致
        if(!password.equals(encodedOldPassword)){
            throw new BizException("原来的密码错误！");
        }
        //判断当前登录的密码和前端传过来的加盐加密后的密码是否一致，也就是新旧密码不能一样。
        if(encodedNewPassword.equals(password)){
            throw new BizException("新密码与旧密码不能一样");
        }else{
            //生成新的盐 默认长度 16位
            salt = new SecureRandomNumberGenerator().nextBytes().toString();
            //设置 hash 算法迭代次数
            int times = 2;
            //得到 hash 后的密码
            encodedNewPassword = new SimpleHash("md5", newpassword, salt, times).toString();
            userService.changePassword(salt ,encodedNewPassword,user);
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return  RespBean.ok("修改成功,请重新登录系统！");
        }
    }
}
