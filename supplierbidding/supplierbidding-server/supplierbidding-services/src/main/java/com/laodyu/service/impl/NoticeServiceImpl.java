package com.laodyu.service.impl;

import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.NoticeDao;
import com.laodyu.entity.Notice;
import com.laodyu.entity.User;
import com.laodyu.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeDao noticeDao;
    @Override
    public RespPage getAllList(int page , int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Page<Notice> allPage = noticeDao.findAll(pageRequest);
        long total = allPage.getTotalElements();
        List<Notice> noticeList = allPage.getContent();
        return new RespPage(total,noticeList);
    }

    @Override
    public void addNotice(Notice notice, User currentUser) {
        String createdby = currentUser.getCode();
        String updatedby = currentUser.getCode();
        Timestamp createdon = new Timestamp(System.currentTimeMillis());
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        notice.setCreatedby(createdby);
        notice.setCreatedon(createdon);
        notice.setUpdatedby(updatedby);
        notice.setUpdatedon(updatedon);
        notice.setApprovestate(ENApproveState.NOTAPPROVE.getValue());
        notice.setCancelstate(ENCancelState.NOCANCEL.getValue());
        noticeDao.save(notice);
    }

    @Override
    public RespPage getNoticeByCode(int page , int size ,String code) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Notice> specification = new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("createdby").as(String.class),code));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Notice> all = noticeDao.findAll(specification, pageRequest);
        long total = all.getTotalElements();
        List<Notice> noticeList = all.getContent();
        return new RespPage(total,noticeList);
    }

    @Override
    public void doApprove(int urid ,String approvestate, User currentUser,String processinstanceid) {
        String updatedby = currentUser.getCode();
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        noticeDao.updateApproveState(urid,approvestate,updatedby,updatedon,processinstanceid);
    }

    @Override
    public RespPage queryNoticeList(Integer page, Integer size, String approvestate, String cancelstate) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("updatedon").descending());
        Specification<Notice> specification = new Specification<Notice>() {
            @Override
            public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("approvestate").as(String.class),approvestate));
                list.add(criteriaBuilder.equal(root.get("cancelstate").as(String.class),cancelstate));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Notice> notices = noticeDao.findAll(specification, pageRequest);
        long total = notices.getTotalElements();
        List<Notice> noticeList = notices.getContent();
        return new RespPage(total,noticeList);
    }
}
