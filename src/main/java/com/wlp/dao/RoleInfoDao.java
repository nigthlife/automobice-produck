package com.wlp.dao;

import com.wlp.bean.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleInfoDao {

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(RoleInfo record);

    /**
     * 可选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(RoleInfo record);

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    int deleteByPrimaryKey(Integer roleId);

    /**
     * 可选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(RoleInfo record);

    /**
     * 查询所有
     *
     * @return
     */
    List<RoleInfo> selectAll();

    /**
     * 获取最新的roleId
     * @Date: 15:12 2021/4/30
     * @param:
     * @return: int
     **/
    int getMax();
}
