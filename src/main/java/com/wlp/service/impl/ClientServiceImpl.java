package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.ClientInfo;
import com.wlp.dao.ClientInfoDao;
import com.wlp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    // 创建dao层对象
    @Autowired
    private ClientInfoDao dao;

    /**
     * 通过主键删除
     *
     * @param clientId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer clientId) {
        return dao.deleteByPrimaryKey(clientId);
    }

    @Override
    public int insert(ClientInfo record) {
        return dao.insert(record);
    }

    @Override
    public int insertSelective(ClientInfo record) {
        return dao.insertSelective(record);
    }

    @Override
    public ClientInfo selectByPrimaryKey(Integer clientId) {
        return dao.selectByPrimaryKey(clientId);
    }

    @Override
    public int updateByPrimaryKeySelective(ClientInfo record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ClientInfo record) {
        return dao.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<ClientInfo> getClientInfoAll(Integer pageIndex, Integer pageSize) {

        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<ClientInfo>(dao.getClientInfoAll());
    }

    @Override
    public int getMaxId() {
        return dao.getMaxId();
    }

    @Override
    public List<ClientInfo> getClinetIdAndName() {
        return dao.getClinetIdAndName();
    }
}
