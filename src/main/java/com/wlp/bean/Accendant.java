package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * accendant
 *
 * @author
 */
@Data
@AllArgsConstructor // 生成全参数构造函数
@NoArgsConstructor // 生成无参构造函数
public class Accendant implements Serializable {

  /** 维修人员Id */
  private Integer accendantId;

  /** 维修人员姓名 */
  private String accendantName;

  /** 维修人员电话 */
  private String phone;

  /**
   * 有参构造方法
   *
   * @param accendantId
   * @param accendantName
   */
  public Accendant(Integer accendantId, String accendantName) {
    this.accendantId = accendantId;
    this.accendantName = accendantName;
  }

  /**
   * 有参构造方法
   *
   * @param accendantName
   * @param phone
   */
  public Accendant(String accendantName, String phone) {
    this.accendantName = accendantName;
    this.phone = phone;
  }

  /**
   * 有参构造方法
   *
   * @param accendantName
   */
  public Accendant(String accendantName) {
    this.accendantName = accendantName;
  }
}
