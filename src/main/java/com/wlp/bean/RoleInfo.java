package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * roleinfo
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo implements Serializable {

  /** 角色Id */
  private Integer roleId;

  /** 角色名 */
  private String roleName;

  /** 角色描述 */
  private String roleDesc;

  /** 是否启用 */
  private Integer isEnable;

  private List<Resources> resourcesList;
}
