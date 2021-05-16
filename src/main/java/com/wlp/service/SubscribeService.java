package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.Subscribe;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName SubscribeService
 * @description
 * @date 2021-04-27 18:01
 * @Version 1.0
 */
public interface SubscribeService {
    /**
     * 获取预约表中所有数据
     * @return
     */
    PageInfo<Subscribe> getSubscribeAll(Integer pageIndex, Integer pageSize);

    /**
     * 更新预约表中信息
     * @return
     */
    int upSubscribe(Subscribe subscribe);

    /**
     * 新增一条预约信息
     * @return
     */
    int inSubscribe(Subscribe subscribe);

    /**
     * 删除一条预约信息
     * @return
     */
    int deSubscribe(Subscribe subscribe);

    /**
     * 获取最新插入id
     * @Date: 10:19 2021/4/30
     * @param:
     * @return: int
     **/
    int getMaxId();
}
