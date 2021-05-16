package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.ClientInfo;

import java.util.List;

public interface ClientService {
    /**
     * 通过主键删除
     *
     * @param clientId
     * @return
     */
    int deleteByPrimaryKey(Integer clientId);

    int insert(ClientInfo record);

    int insertSelective(ClientInfo record);

    ClientInfo selectByPrimaryKey(Integer clientId);

    int updateByPrimaryKeySelective(ClientInfo record);

    int updateByPrimaryKey(ClientInfo record);

    /**
     * 查询所以客户信息
     *
     * @Date: 17:41 2021/4/29
     * @param:
     * @return: java.util.List<com.wlp.bean.ClientInfo>
     **/
    PageInfo<ClientInfo> getClientInfoAll(Integer pageIndex, Integer pageSize);

    /**
     * 获取最先插入数据id
     *
     * @Date: 20:48 2021/4/29
     * @param:
     * @return: int
     **/
    int getMaxId();

    /**
     * 获取所有客户名称与id
     * @Date: 17:59 2021/5/2
     * @param:
     * @return: java.util.List<com.wlp.bean.ClientInfo>
     **/
    List<ClientInfo> getClinetIdAndName();
}
