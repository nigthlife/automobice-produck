package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：仓库表
 *
 * @author 武乐萍
 * @ClassName partsrepertory
 * @description
 * @date 2021-05-03 13:51
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class partsRepertory {

    /*
     * @repertoryId: 编号
     **/
    private Integer repertoryId;

    /*
     * @partsId: 零件id
     **/
    private Integer partsId;

    /*
     * @partsPrice: 零件售价
     **/
    private Integer partsPrice;

    /*
     * @count: 库存数量
     **/
    private Integer count;

    /*
     * @repertoryId: 零件信息
     **/
    private PartsInfo partsInfo;

}
