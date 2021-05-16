package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * carinfo
 *
 * @author
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarInfo implements Serializable {
    /**
     * @carid:信息id
     */
    private Integer carId;

    /**
     * @clienteleId:客户id(外键客户表到客户id)
     */
    private Integer clientId;


    /**
     * @carBrand:汽车品牌
     */
    private String carBrand;


    /**
     * @carModel:汽车型号（车牌号）
     */
    private String carNumber;

    /**
     * @clientInfo:客户信息
     */
    private ClientInfo clientInfo;
}
