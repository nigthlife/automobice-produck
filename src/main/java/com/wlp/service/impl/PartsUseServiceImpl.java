package com.wlp.service.impl;

import com.wlp.bean.PartsUse;
import com.wlp.dao.PartsUseDao;
import com.wlp.service.PartsUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartsUseServiceImpl implements PartsUseService {

  // 创建Dao层对象
  @Autowired private PartsUseDao dao;

  /**
   * 根据主键删除
   *
   * @param useId
   * @return
   */
  @Override
  public int deleteByPrimaryKey(Integer useId) {
    return dao.deleteByPrimaryKey(useId);
  }

  /**
   * 插入
   *
   * @param record
   * @return
   */
  @Override
  public int insert(PartsUse record) {
    return insert(record);
  }

  /**
   * 选择性插入
   *
   * @param record
   * @return
   */
  @Override
  public int insertSelective(PartsUse record) {
    return dao.insertSelective(record);
  }

  /**
   * 根据主键查询
   *
   * @param useId
   * @return
   */
  @Override
  public PartsUse selectByPrimaryKey(Integer useId) {
    return dao.selectByPrimaryKey(useId);
  }

  /**
   * 根据主键可选择性更新
   *
   * @param record
   * @return
   */
  @Override
  public int updateByPrimaryKeySelective(PartsUse record) {
    return dao.updateByPrimaryKeySelective(record);
  }

  /**
   * 根据主键更新
   *
   * @param record
   * @return
   */
  @Override
  public int updateByPrimaryKey(PartsUse record) {
    return dao.updateByPrimaryKey(record);
  }
}
