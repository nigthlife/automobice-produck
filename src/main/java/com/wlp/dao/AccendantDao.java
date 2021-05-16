package com.wlp.dao;

import com.wlp.bean.Accendant;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccendantDao {

    /**
     * 查询维修人员表中所有信息
     * @Date: 14:05 2021/4/29
     * @param:
     * @return: java.util.List<com.wlp.bean.Accendant>
     **/
    List<Accendant> selectAll();

    /**
     * 根据维修人员id 查询指定人员表中信息
     * @Date: 14:03 2021/4/29
     * @param: accendantId
     * @return: int
     **/
    Accendant selectByPrimaryKey(Integer accendantId);

    /**
     * 根据维修人员id 删除指定人员
     * @Date: 14:06 2021/4/29
     * @param: accendantId
     * @return: int
     **/
    int deleteByPrimaryKey(Integer accendantId);

    /**
     * 向维修人员表中插入一条信息
     * @Date: 14:08 2021/4/29
     * @param: record
     * @return: int
     **/
    int insert(Accendant record);

    /**
     * 向维修人员表中插入指定信息
     * @Date: 14:08 2021/4/29
     * @param: record
     * @return: int
     **/
    int insertSelective(Accendant record);


    /**
     * 更新维修人员表中不为空的信息
     * @Date: 14:10 2021/4/29
     * @param: record
     * @return: int
     **/
    int updateByPrimaryKeySelective(Accendant record);

    /**
     * 更新维修人员表中信息
     * @Date: 14:10 2021/4/29
     * @param: record
     * @return: int
     **/
    int updateByPrimaryKey(Accendant record);

    /**
     * 获取最新插入的数据id
     * @Date: 15:11 2021/4/29
     * @param:
     * @return: int
     **/
    int getAccMax();
}
