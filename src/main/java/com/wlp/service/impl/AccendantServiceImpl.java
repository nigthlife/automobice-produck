package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.Accendant;
import com.wlp.dao.AccendantDao;
import com.wlp.service.AccendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccendantServiceImpl implements AccendantService {

    // 创建持久层对象
    @Autowired
    private AccendantDao dao;

    @Override
    public int deleteByPrimaryKey(Integer accendantid) {
        return dao.deleteByPrimaryKey(accendantid);
    }

    @Override
    public int insert(Accendant record) {
        return dao.insert(record);
    }

    @Override
    public int insertSelective(Accendant record) {
        return dao.insertSelective(record);
    }

    @Override
    public PageInfo<Accendant> selectAll(Integer pageIndex, Integer pageSize) {

        if (pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        return new PageInfo<Accendant>(dao.selectAll());
    }

    @Override
    public Accendant selectByPrimaryKey(Integer accendantid) {
        return dao.selectByPrimaryKey(accendantid);
    }

    @Override
    public int updateByPrimaryKeySelective(Accendant record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Accendant record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public int getAccMax() {
        return dao.getAccMax();
    }
}
