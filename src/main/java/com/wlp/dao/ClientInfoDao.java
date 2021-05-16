package com.wlp.dao;

import com.wlp.bean.ClientInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientInfoDao {

    /**
     * 通过主键删除
     *
     * @param clientId
     * @return
     */
    int deleteByPrimaryKey(Integer clientId);

    /**
     * 新增客户
     *
     * @Date: 17:37 2021/4/29
     * @param: record
     * @return: int
     **/
    int insert(ClientInfo record);

    /**
     * 新增客户
     *
     * @Date: 17:37 2021/4/29
     * @param: record
     * @return: int
     **/
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
    List<ClientInfo> getClientInfoAll();

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
