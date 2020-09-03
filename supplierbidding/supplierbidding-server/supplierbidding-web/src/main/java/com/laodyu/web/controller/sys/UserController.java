package com.laodyu.web.controller.sys;

import com.laodyu.commons.constant.ENRole;
import com.laodyu.commons.constant.ENUserState;
import com.laodyu.commons.exception.BizException;
import com.laodyu.commons.resp.RespBean;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.User;
import com.laodyu.service.ExpertService;
import com.laodyu.service.UserRoleService;
import com.laodyu.service.UserService;
import com.laodyu.web.util.ActivitiUtils;
import com.laodyu.web.util.SessionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/12
 * @Version 1.0
 **/
@RestController
@RequestMapping("/system/userinfo")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    ExpertService expertService;
    @GetMapping("/")
    public RespPage getAllUser(@RequestParam(defaultValue = "1") Integer page,
                                 @RequestParam(defaultValue = "10")Integer size){
        if(page!= null && size != null){
            page = page-1;
        }
        RespPage respPage = userService.findAll(page, size);
        return respPage;
    }

    @PostMapping("/")
    public RespBean addUser(@RequestParam Map<String, String> params){
        String code = params.get("code");
        if(StringUtils.isEmpty(code)){
            throw new BizException("用户名不能为空！");
        }
        if(userService.findByCode(code)!= null){
            throw new BizException("该用户名已注册！");
        }
        //生成盐 默认长度 16位
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        //设置 hash 算法迭代次数
        int times = 2;
        //得到 hash 后的密码
        String encodedPassword = new SimpleHash("md5", params.get("password"), salt, times).toString();
        userService.AddUser(code,encodedPassword,salt, ENUserState.NORMAL.getValue(), SessionUtil.getSession());
        return RespBean.ok("增加成功");
    }
    @PutMapping("/{status}")
    public RespBean changeStatus(@PathVariable String status,String []codes){
        RespBean respBean = userService.changeUserStatus(codes, status, SessionUtil.getSession());
        return respBean;
    }

    @PutMapping("/changeRoles")
    public RespBean changeRoles(Integer userid,Integer [] rolesid){
        if(rolesid!=null&&rolesid.length>0){
            RespBean respBean = userRoleService.upateUserRole(userid, rolesid, SessionUtil.getSession());
            //判断roleid 是不是 招标商 ,专家
            User user = userService.findByUrid(userid);
            String code = user.getCode();
            if(rolesid.equals(ENRole.ROLE_TENDERING.getValue())||rolesid.equals(ENRole.ROLE_EXPERT.getValue())){
                //查看工作流中的用户表是否存在该用户
                boolean flag = ActivitiUtils.isExist(code);
                if(!flag){
                    //不存在则往工作流中添加用户
                    ActivitiUtils.addUser(code);
                }
            }
            return respBean;
        }
        return RespBean.error("一个用户至少对应一个角色！");
    }


}
