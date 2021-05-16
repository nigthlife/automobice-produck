package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * clientinfo
 *
 * @author
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo implements Serializable {
    /**
     * @clienteleId:客户id
     */
    private Integer clientId;

    /**
     * @clienteleName:客户名称
     */
    private String clientName;

    /**
     * @sex:客户性别
     */
    private String sex;

    /**
     * @phone 客户手机号
     */
    private String phone;

    /**
     * @createData:创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDate;
}
