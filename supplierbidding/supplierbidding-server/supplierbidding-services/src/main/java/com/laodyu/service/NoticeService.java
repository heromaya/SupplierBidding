package com.laodyu.service;

import com.laodyu.commons.resp.RespPage;
import com.laodyu.entity.Notice;
import com.laodyu.entity.User;

/**
 * @InterfaceName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
public interface NoticeService {
    RespPage getAllList(int page , int size);

    void addNotice(Notice notice, User currentUser);

    RespPage getNoticeByCode(int page , int size ,String code);

    void doApprove(int urid , String approveState, User currentUser,String processinstanceid);

    RespPage queryNoticeList(Integer page, Integer size, String approvestate, String cancelstate);
}
