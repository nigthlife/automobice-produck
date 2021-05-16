package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.RoleInfo;
import com.wlp.bean.Router;
import com.wlp.bean.UserInfo;
import com.wlp.dao.UserInfoDao;
import com.wlp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    // 创建Dao层对象
    @Autowired
    private UserInfoDao dao;

    /**
     * 插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(UserInfo record) {
        return dao.insert(record);
    }

    /**
     * 可选择性插入
     *
     * @param record
     * @return
     */
    @Override
    public int insertSelective(UserInfo record) {
        return dao.insertSelective(record);
    }

    /**
     * 删除(逻辑删除不用此方法)
     *
     * @param userId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return dao.deleteByPrimaryKey(userId);
    }

    /**
     * 可选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(UserInfo record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public PageInfo<UserInfo> selectAll(Integer pageIndex, Integer pageSize) {
        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        List<UserInfo> userInfos = dao.selectAll();

        return new PageInfo<UserInfo>(userInfos);
    }

    /**
     * 登录验证
     *
     * @param u
     */
    @Override
    public UserInfo verify(UserInfo u) {
        return dao.verify(u);
    }

    @Override
    public List<RoleInfo> getRoleByUserId(int userId) {
        return dao.getRoleByUserId(userId);
    }

    @Override
    public List<Router> getRouter(int userId) {
        return dao.getRouter(userId);
    }

    @Override
    public List<RoleInfo> getRole(int userId) {
        return dao.getRole(userId);
    }

    @Override
    public List<UserInfo> getUserId() {
        return dao.getUserId();
    }

    @Override
    public Integer getUserIdMax() {
        return dao.getUserIdMax();
    }
}
