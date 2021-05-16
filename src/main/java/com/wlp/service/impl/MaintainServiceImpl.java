package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.Maintain;
import com.wlp.dao.MaintainDao;
import com.wlp.service.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName MaintainServiceImpl
 * @description
 * @date 2021-04-29 19:33
 * @Version 1.0
 */
@Service
public class MaintainServiceImpl implements MaintainService {

    @Autowired
    private MaintainDao mapper;

    @Override
    public PageInfo<Maintain> getMainAll(Integer pageIndex, Integer pageSize) {

        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<Maintain>(mapper.getMainAll());
    }

    @Override
    public List<Maintain> getAccendant() {
        return mapper.getAccendant();
    }

    @Override
    public PageInfo<Maintain> getFinish(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<>(mapper.getFinish());
    }

    @Override
    public int dispatchAccendant(Maintain maintain) {
        return mapper.dispatchAccendant(maintain);
    }

    @Override
    public int addMaintain(Maintain maintain) {
        return mapper.addMaintain(maintain);
    }

    @Override
    public int getMax() {
        return mapper.getMax();
    }

    @Override
    public Integer upMaintainNo(Map<String, Object> map) {
        return mapper.upMaintainNo(map);
    }

    @Override
    public Integer upMaintain(Maintain maintain) {
        return mapper.upMaintain(maintain);
    }
}
