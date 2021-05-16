package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsInfo
 * @description
 * @date 2021-04-29 18:18
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartsInfo {
    /**
     * @partsId:配件id
     */
    private Integer partsId;


    /**
     * @partsName:配件名称
     */
    private String partsName;

    /**
     * @price:价格
     */
    private Double price;

    /**
     * @count: 数量
     **/
    private Integer count;

    public PartsInfo(Integer partsId) {
        this.partsId = partsId;
    }

}
