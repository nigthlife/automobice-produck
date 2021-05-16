package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import com.wlp.bean.partsRepertory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsRepertoryService
 * @description
 * @date 2021-05-03 14:13
 * @Version 1.0
 */
public interface PartsRepertoryService {

    /**
     * 获取仓库中所有信息
     * @Date: 14:01 2021/5/3
     * @param:
     * @return: java.util.List<com.wlp.bean.partsrepertory>
     **/
    PageInfo<partsRepertory> selectAll(Integer pageIndex, Integer pageSize);

    /**
     * 查询零件是否在仓库存在
     * @Date: 18:14 2021/5/3
     * @param:
     * @return: int
     **/
    Integer selectPartsExist(@Param("partsId") int id);

    /**
     * 增加零件数量
     * @Date: 18:24 2021/5/3
     * @param:
     * @return: int
     **/
    int upCount(partsRepertory repertory);

    /**
     * 获取仓库中所有零件
     * @Date: 13:14 2021/5/5
     * @param:
     * @return: java.util.List<com.wlp.bean.PartsInfo>
     **/
    List<PartsInfo> selectPartsAll();

    /**
     * 新增一个零件信息
     * @Date: 18:26 2021/5/3
     * @param: repertory
     * @return: int
     **/
    int inPartsInfo(partsRepertory repertory);

    /**
     * 更新售价信息
     * @Date: 20:27 2021/5/3
     * @param:
     * @return: int
     **/
    int uppartsPrice(int partsId,int partsPrice);
}
