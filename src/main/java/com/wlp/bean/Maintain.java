package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName Maintain
 * @description
 * @date 2021-04-29 18:18
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maintain {
    /**
     * @maintainId：记录号
     */
    private Integer Id;

    /**
     * @maintainNo:维修编号
     */
    private String maintainNo;

    /**
     * @clienteleId:汽车id（外键）
     */
    private Integer carId;

    /**
     * @maintainDate:维修开始时间
     */
    private Date maintainDate;

    /**
     * @maintainDate:完成时间
     */
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishDate;

    /**
     * @accendantId:维修人员id（外键）
     */
    private Integer accendantId;

    /**
     * @maintainState：维修状态
     */
    private Integer State;

    /**
     * @partsId:维修配件(外键)
     */
    private Integer usePartsId;

    /**
     * @maintainCost(维修收益)
     */
    private Integer maintainCost;

    /**
     * @maintainInfo:维修描述
     */
    private String maintainInfo;

    /**
     * TODO 属性描述
     * 客户信息
     */
    private CarInfo carInfo;

    /**
     * TODO 属性描述
     * 零件信息
     */
    private PartsInfo partsInfo;

    /**
     * TODO 属性描述
     * 维修人员信息
     */
    private Accendant accendant;

    public Maintain(Integer id, Integer accendantId,Integer usePartsId) {
        Id = id;
        this.accendantId = accendantId;
        this.usePartsId = usePartsId;
    }

}
