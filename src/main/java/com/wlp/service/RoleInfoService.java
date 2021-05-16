package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.RoleInfo;

public interface RoleInfoService {

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
     * @param pageIndex
     * @param pageSize
     */
    PageInfo<RoleInfo> selectAll(Integer pageIndex, Integer pageSize);

    /**
     * 获取最新的roleId
     * @Date: 15:12 2021/4/30
     * @param:
     * @return: int
     **/
    int getMax();
}
