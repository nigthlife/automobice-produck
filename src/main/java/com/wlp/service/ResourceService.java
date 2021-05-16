package com.wlp.service;

import com.wlp.bean.Resources;

import java.util.List;

public interface ResourceService {

  /**
   * 插入
   *
   * @param record
   * @return
   */
  int insert(Resources record);

  /**
   * 可选择性插入
   *
   * @param record
   * @return
   */
  int insertSelective(Resources record);

  /**
   * 删除
   *
   * @param resId
   * @return
   */
  int deleteByPrimaryKey(Integer resId);

  /**
   * 可选择性更新
   *
   * @param record
   * @return
   */
  int updateByPrimaryKeySelective(Resources record);

  /**
   * 查询所有
   *
   * @return
   */
  List<Resources> selectAll();
}
