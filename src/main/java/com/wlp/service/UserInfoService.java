package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.RoleInfo;
import com.wlp.bean.Router;
import com.wlp.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoService {

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(UserInfo record);

    /**
     * 可选择性插入
     *
     * @param record
     * @return
     */
    int insertSelective(UserInfo record);

    /**
     * 删除(逻辑删除不用此方法)
     *
     * @param userId
     * @return
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * 可选择性更新
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * 查询所有
     *
     * @return
     */
    PageInfo<UserInfo> selectAll(Integer pageIndex, Integer pageSize);

    /**
     * 登录验证
     */
    UserInfo verify(UserInfo u);

    /**
     * 根据用户id获取当前用户角色所有拥有的资源
     *
     * @param userId
     * @return
     */
    List<RoleInfo> getRoleByUserId(@Param("userId") int userId);

    /**
     * 根据用户id获取该用户的路由表
     * @param userId
     * @return
     */
    List<Router> getRouter(int userId);

    List<RoleInfo> getRole(int  userId);

    /**
     * 获取所有用户id
     * @Date: 22:17 2021/4/27
     * @param:
     * @return: java.util.List<com.wlp.bean.UserInfo>
     **/
    List<UserInfo> getUserId();

    Integer getUserIdMax();
}
