package com.laodyu.service.impl;


import com.laodyu.commons.constant.ENApproveState;
import com.laodyu.commons.constant.ENCancelState;
import com.laodyu.commons.resp.RespPage;
import com.laodyu.dao.SupplierDao;
import com.laodyu.entity.Supplier;
import com.laodyu.entity.User;
import com.laodyu.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierDao supplierDao;

    /***
     * 供应商注册
     * @param params
     * @param password
     * @param salt
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void AddSupplier(Map<String, String> params, String password, String salt,String processInstanceId) {
        Supplier supplier = new Supplier();
        String userid = params.get("userid");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        supplier.setName(params.get("companyName"));
        supplier.setAddress(params.get("address"));
        supplier.setUserid(userid);
        supplier.setPassword(password);
        supplier.setSalt(salt);
        supplier.setTaxid(params.get("taxid"));
        supplier.setTelephone(params.get("telephone"));
        supplier.setEmail(params.get("email"));
        supplier.setProcessInstenceid(processInstanceId);
        //设置审批中
        supplier.setApprovestate(ENApproveState.APPROVING.getValue());
        //设置未作废
        supplier.setCancelstate(ENCancelState.NOCANCEL.getValue());
        supplier.setCreatedby(userid);
        supplier.setUpdatedby(userid);
        supplier.setCreatedon(time);
        supplier.setUpdatedon(time);
        supplierDao.save(supplier);

    }

    /***
     * 通过userid 获取supplier
     * @param userid
     * @return
     */
    @Override
    public Supplier findByUserid(String userid) {
        return supplierDao.findByUserid(userid);
    }

    @Override
    public Supplier findByEmail(String email) {
        return supplierDao.findByEmail(email);
    }

    @Override
    public Supplier findByTelephone(String telephone) {
        return supplierDao.findByTelephone(telephone);
    }

    @Override
    public RespPage findAllSupplier(String approveState , String cancelState ,int page , int size) {

        PageRequest pageRequest = PageRequest.of(page, size,Sort.by("updatedon").descending());

        Specification<Supplier> specification = new Specification<Supplier>() {
            @Override
            public Predicate toPredicate(Root<Supplier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("approvestate").as(String.class),approveState));
                list.add(criteriaBuilder.equal(root.get("cancelstate").as(String.class),cancelState));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Supplier> allPage = supplierDao.findAll(specification, pageRequest);
        long total = allPage.getTotalElements();
        List<Supplier> supplierList = allPage.getContent();
        return new RespPage(total,supplierList);
    }

    /***
     * 获取所有的供应商信息
     * @param page
     * @param size
     * @return
     */
    @Override
    public RespPage getAllSupplier(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size,Sort.by("updatedon").descending());
        Page<Supplier> all = supplierDao.findAll(pageRequest);
        long total = all.getTotalElements();
        List<Supplier> supplierList = all.getContent();
        return new RespPage(total,supplierList);
    }

    @Override
    public void updateApprovestate(String userid, String approvestate, User user) {
        String updatedby = user.getCode();
        Timestamp updatedon = new Timestamp(System.currentTimeMillis());
        supplierDao.updateApprovestate(userid,approvestate,updatedby,updatedon);
    }

    @Override
    public Supplier findByUrid(int supplierid) {
        return supplierDao.findByUrid(supplierid);
    }
}
