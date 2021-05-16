package com.wlp.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 *
 * @author 武乐萍
 * @modifier 武乐萍
 * @date 2021-04-18 16:55
 * @Version V1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Router {

    private String path;

    private String name;

    private String component;

    private String redirect;

    private String level;

    private String parentId;

    private Meta meta;

}
