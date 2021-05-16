package com.wlp.service;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsInfoSercive
 * @description
 * @date 2021-04-30 14:51
 * @Version 1.0
 */
public interface PartsInfoService {

    /**
     * 插入零件信息
     * @Date: 14:40 2021/4/30
     * @param: partsInfo
     * @return: int
     **/
    int inPartsInfo(PartsInfo partsInfo);

    /**
     * 删除零件信息
     * @Date: 14:41 2021/4/30
     * @param: id
     * @return: int
     **/
    int dePartsInfo(@Param("partsId") int id);


    /**
     * 更新零件信息
     * @Date: 14:41 2021/4/30
     * @param: partsInfo
     * @return: int
     **/
    int upPartsInfo(PartsInfo partsInfo);

    /**
     * 查询零件表中所有数据
     * @Date: 14:39 2021/4/30
     * @param:
     * @return: java.util.List<com.wlp.bean.PartsInfo>
     **/
    PageInfo<PartsInfo> selectAll(Integer pageIndex, Integer pageSize);

    /**
     * 查询最新插入信息id
     * @Date: 14:42 2021/4/30
     * @param:
     * @return: int
     **/
    int getMax();

}
