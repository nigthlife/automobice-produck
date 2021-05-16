package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.Subscribe;
import com.wlp.dao.SubscribeDao;
import com.wlp.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName SubscribeServiceImpl
 * @description
 * @date 2021-04-27 18:02
 * @Version 1.0
 */
@Service
@Transactional
public class SubscribeServiceImpl implements SubscribeService {

    @Autowired
    private SubscribeDao subMapper;

    @Override
    public PageInfo<Subscribe> getSubscribeAll(Integer pageIndex, Integer pageSize) {

        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<>(subMapper.getSubscribeAll());
    }

    @Override
    public int upSubscribe(Subscribe subscribe) {
        return subMapper.upSubscribe(subscribe);
    }


    @Override
    public int inSubscribe(Subscribe subscribe) {
        return subMapper.inSubscribe(subscribe);
    }

    /**
     *
     * @Author: wlp
     * @Description: 夜生情
     * @Date: 10:11 2021/4/26
     * @param: subscribe
     * @return: int
     **/
    @Override
    public int deSubscribe(Subscribe subscribe) {
        return subMapper.deSubscribe(subscribe);
    }

    @Override
    public int getMaxId() {
        return subMapper.getMaxId();
    }
}
