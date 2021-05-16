package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.Maintain;

import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName MaintainService
 * @description
 * @date 2021-04-29 19:32
 * @Version 1.0
 */
public interface MaintainService {

    /**
     * 获取维修记录表中所有信息
     * 获取工单处理表中所有信息
     * @return
     */
    PageInfo<Maintain> getMainAll(Integer pageIndex, Integer pageSize);

    /**
     * 查询未指派员工的工单
     * @return
     */
    List<Maintain> getAccendant();

    /**
     * 查询已完成的工单
     * @Author wlp
     * @Description //TODO 夜生情
     * @Date 9:57 2021/4/26
     * @param
     * @return java.util.List<com.wlp.pojo.Maintain>
     **/
    PageInfo<Maintain> getFinish(Integer pageIndex, Integer pageSize);

    /**
     * 派遣维修人员
     * @param maintain
     * @return
     */
    int dispatchAccendant(Maintain maintain);

    /**
     * 新增工单
     * @Date: 8:39 2021/5/5
     * @param: maintain
     * @return: int
     **/
    int addMaintain(Maintain maintain);

    /**
     * 获取最先插入数据id
     * @Date: 8:50 2021/5/5
     * @param:
     * @return: int
     **/
    int getMax();

    /**
     * 更新维修编号
     * @Date: 8:55 2021/5/5
     * @param: Id
     * @return: int
     **/
    Integer upMaintainNo(Map<String,Object> map);

    /**
     * 更新工单信息
     * @Date: 14:06 2021/5/5
     * @param: maintain
     * @return: int
     **/
    Integer upMaintain(Maintain maintain);
}
