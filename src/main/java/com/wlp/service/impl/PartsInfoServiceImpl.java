package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import com.wlp.dao.PartsInfoDao;
import com.wlp.service.PartsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsInfoServiceImpl
 * @description
 * @date 2021-04-30 14:52
 * @Version 1.0
 */
@Service
public class PartsInfoServiceImpl implements PartsInfoService {

    @Autowired
    private PartsInfoDao parts;

    @Override
    public int inPartsInfo(PartsInfo partsInfo) {
        return parts.inPartsInfo(partsInfo);
    }

    @Override
    public int dePartsInfo(int id) {
        return parts.dePartsInfo(id);
    }

    @Override
    public int upPartsInfo(PartsInfo partsInfo) {
        return parts.upPartsInfo(partsInfo);
    }

    @Override
    public PageInfo<PartsInfo> selectAll(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo(parts.selectAll());
    }

    @Override
    public int getMax() {
        return parts.getMax();
    }
}
