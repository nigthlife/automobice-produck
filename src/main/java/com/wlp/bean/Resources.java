package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * resources
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resources implements Serializable {

  /** 资源Id */
  private Integer resId;

  /** 资源名 */
  private String resName;

  /** 资源地址 */
  private String resUrl;

  /** 资源等级 */
  private Integer level;

  /** 父级资源ID */
  private Integer parentId;

  /** 是否启用 */
  private Integer isEnable;

  public Resources(String resName, String resUrl) {
    this.resName = resName;
    this.resUrl = resUrl;
  }

  public Resources(Integer resId, String resName, String resUrl) {
    this.resId = resId;
    this.resName = resName;
    this.resUrl = resUrl;
  }
}
