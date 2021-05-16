package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * userinfo
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

  /** 用户Id */
  private Integer userId;

  /** 用户名 */
  private String userName;

  /** 登录名 */
  private String loginName;

  /** 用户密码 */
  private String userPwd;

  /** 创建日期 */
  private Date createDate;

  /** 登录状态 */
  private Integer loginState;

  /** 是否删除 */
  private Integer isDelete;

  private List<RoleInfo> roleInfoList;

  private String token;

  private int code;

  public UserInfo(String loginName, String userPwd) {
    this.loginName = loginName;
    this.userPwd = userPwd;
  }
}
