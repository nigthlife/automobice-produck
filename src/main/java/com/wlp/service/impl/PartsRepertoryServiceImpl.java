package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import com.wlp.bean.partsRepertory;
import com.wlp.dao.PartsRepertoryDao;
import com.wlp.service.PartsRepertoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsRepertoryServiceImpl
 * @description
 * @date 2021-05-03 14:14
 * @Version 1.0
 */
@Service
public class PartsRepertoryServiceImpl implements PartsRepertoryService {

    @Autowired
    private PartsRepertoryDao repertoryDao;

    @Override
    public PageInfo<partsRepertory> selectAll(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<>(repertoryDao.selectAll());
    }

    @Override
    public Integer selectPartsExist(int id) {
        return repertoryDao.selectPartsExist(id);
    }

    @Override
    public int upCount(partsRepertory repertory) {
        return repertoryDao.upCount(repertory);
    }

    @Override
    public List<PartsInfo> selectPartsAll() {
        return repertoryDao.selectPartsAll();
    }

    @Override
    public int inPartsInfo(partsRepertory repertory) {
        return repertoryDao.inPartsInfo(repertory);
    }

    @Override
    public int uppartsPrice(int partsId, int partsPrice) {
        return repertoryDao.uppartsPrice(partsId,partsPrice);
    }
}
