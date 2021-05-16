package com.wlp.controller;

import com.wlp.service.PartsUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName PartsUseController
 * @description
 * @date 2021-04-29 18:09
 * @Version 1.0
 */
@Controller
@RequestMapping("/part")
@CrossOrigin
public class PartsUseController {

    @Autowired
    private PartsUseService service;

    /**
     * 分页获取客户表中所有信息
     * @Date: 17:50 2021/4/29
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getPartAll")
    public @ResponseBody
    Map<String,Object> getClientInfo(@Nullable @RequestParam("page") Integer pageIndex,
                                     @Nullable @RequestParam("limit") Integer pageSize){

        Map<String,Object> map = new HashMap<>();

//        PageInfo<ClientInfo> clientInfoAll = service.selectByPrimaryKey();
//
//        map.put("items",clientInfoAll.getList());
//        map.put("total",clientInfoAll.getTotal());

        return map;
    }

}
