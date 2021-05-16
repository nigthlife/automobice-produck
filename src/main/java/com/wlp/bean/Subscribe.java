package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Date: 17:55 2021/4/27
 * @param: null
 * @return:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscribe implements Serializable {

    /**
     * @subscribeId:记录id
     */
    private Integer Id;

    /**
     * @cilenteleId:客户id（外键）
     */
    private Integer carId;

    /**
     * @subscribeDate:预约时间
     */
    private Date subscribeDate;


    /**
     * @subscribeInfo:预约描述
     */
    private String subscribeInfo;

    /**
     * 客户信息
     */
    private ClientInfo clientInfoList;

    /**
     * 汽车信息
     */
    private CarInfo carInfo;

    public Subscribe(Integer id) {
        Id = id;
    }
}
