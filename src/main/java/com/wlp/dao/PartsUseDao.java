package com.wlp.dao;

import com.wlp.bean.PartsUse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartsUseDao {

    /**
     * 根据主键删除
     *
     * @param useId
     * @return
     */
    int deleteByPrimaryKey(Integer useId);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(PartsUse record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(PartsUse record);

    /**
     * 根据主键查询
     *
     * @param useId
     * @return
     */
    PartsUse selectByPrimaryKey(Integer useId);

    List<PartsUse> selectAll();

    /**
     * 根据主键可选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PartsUse record);

    /**
     * 根据主键更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(PartsUse record);


}
