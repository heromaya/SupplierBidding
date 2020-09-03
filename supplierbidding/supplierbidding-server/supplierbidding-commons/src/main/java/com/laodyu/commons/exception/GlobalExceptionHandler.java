package com.laodyu.commons.exception;

import com.laodyu.commons.resp.RespBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;


/**
 * @ClassName 全局异常处理
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/30
 * @Version 1.0
 **/
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private String message;

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public RespBean BizException(BizException e) {
        logger.error(e.getErrorMsg());
        return RespBean.error(e.getErrorMsg());
    }


    /***
     * 空指针
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public RespBean execeptionHandle(NullPointerException e) {
        message = "空指针异常";
        logger.error(message);
        return RespBean.error(message);
    }

    /***
     * 违反唯一
     */
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public RespBean handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        e.printStackTrace();
        /***
         * 第一个getCause: org.hibernate.exception.ConstraintViolationException: could not execute statement
         * 第二个getCause: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '系统管理员' for key 'name'
         */
        Throwable cause = e.getCause().getCause();
        if (cause instanceof SQLIntegrityConstraintViolationException) {
            String errMsg = ((SQLIntegrityConstraintViolationException)cause).getMessage();
            if(StringUtils.isNotBlank(errMsg)){
                if(errMsg.indexOf("Cannot delete or update a parent row")!=-1){
                    //外键约束
                    message = "该记录已经被引用！无法删除！";
                }else if(errMsg.indexOf("Duplicate entry")!=-1){
                    //唯一性约束
                    if(errMsg.indexOf("name")!= -1){
                        message = "名称重复！";
                    }else if (errMsg.indexOf("code")!= -1){
                        message = "代码重复！";
                    }
                }
            }
            logger.error(message);
            return RespBean.error(message);
        }
        logger.error("出现异常："+e.getCause().getMessage());
       return RespBean.error("出现异常："+e.getCause().getMessage());
    }

    /***
     * 未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespBean execeptionHandle(Exception e) {
        message = "未知异常！" + e.getCause().getMessage();
        logger.error(message);
        return RespBean.error(message);
    }
}
