package com.wlp.service.impl;

import com.wlp.bean.Resources;
import com.wlp.dao.ResourcesDao;
import com.wlp.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

  // 创建dao层对象
  @Autowired private ResourcesDao dao;

  /**
   * 插入
   *
   * @param record
   * @return
   */
  @Override
  public int insert(Resources record) {
    return dao.insert(record);
  }

  /**
   * 可选择性插入
   *
   * @param record
   * @return
   */
  @Override
  public int insertSelective(Resources record) {
    return dao.insertSelective(record);
  }

  /**
   * 删除
   *
   * @param resId
   * @return
   */
  @Override
  public int deleteByPrimaryKey(Integer resId) {
    return dao.deleteByPrimaryKey(resId);
  }

  /**
   * 可选择性更新
   *
   * @param record
   * @return
   */
  @Override
  public int updateByPrimaryKeySelective(Resources record) {
    return dao.updateByPrimaryKeySelective(record);
  }

  /**
   * 查询所有
   *
   * @return
   */
  @Override
  public List<Resources> selectAll() {
    return dao.selectAll();
  }
}
