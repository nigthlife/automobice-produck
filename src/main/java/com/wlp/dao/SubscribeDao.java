package com.wlp.dao;

import com.wlp.bean.Subscribe;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeDao {
    /**
     * 获取预约表中所有数据
     * @return
     */
    List<Subscribe> getSubscribeAll();

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
