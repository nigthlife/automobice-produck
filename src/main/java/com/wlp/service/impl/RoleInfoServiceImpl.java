package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.RoleInfo;
import com.wlp.dao.RoleInfoDao;
import com.wlp.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleInfoServiceImpl implements RoleInfoService {

    // 创建dao层对象
    @Autowired
    private RoleInfoDao dao;

    /**
     * 插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(RoleInfo record) {
        return dao.insert(record);
    }

    /**
     * 可选择性插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(RoleInfo record) {
        return dao.insertSelective(record);
    }

    /**
     * 删除
     *
     * @param roleId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer roleId) {
        return dao.deleteByPrimaryKey(roleId);
    }

    /**
     * 可选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(RoleInfo record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询所有
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<RoleInfo> selectAll(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<>(dao.selectAll());
    }

    @Override
    public int getMax() {
        return dao.getMax();
    }
}
