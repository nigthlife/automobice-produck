package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * partsuse
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartsUse implements Serializable {

  /** 配件使用记录Id */
  private Integer useId;

  /** 维修记录Id */
  private Integer maintainId;

  /** 配件Id */
  private Integer partsId;
}
