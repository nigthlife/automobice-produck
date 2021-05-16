package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.ClientInfo;
import com.wlp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName ClientInfoController
 * @description
 * @date 2021-04-29 17:45
 * @Version 1.0
 */
@Controller
@RequestMapping("/client")
@CrossOrigin
public class ClientInfoController {

    @Autowired
    private ClientService service;

    /**
     * 分页获取客户表中所有信息
     * @Date: 17:50 2021/4/29
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getCliAll")
    public @ResponseBody Map<String,Object> getClientInfo(@Nullable @RequestParam("page") Integer pageIndex,
                                                          @Nullable @RequestParam("limit") Integer pageSize){

        Map<String,Object> map = new HashMap<>();

        PageInfo<ClientInfo> clientInfoAll = service.getClientInfoAll(pageIndex, pageSize);
        System.err.println(clientInfoAll.getList());
        map.put("items",clientInfoAll.getList());
        map.put("total",clientInfoAll.getTotal());

        return map;
    }

    /**
     * 添加一条客户信息
     * @Date: 17:53 2021/4/29
     * @param: clientInfo
     * @return: int
     **/
    @RequestMapping("/addClient")
    public @ResponseBody int addClientData(ClientInfo clientInfo){

        if (clientInfo != null){

            if (service.insert(clientInfo) > 0){

                return service.getMaxId();
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    /**
     * 删除客户信息
     * @Date: 17:55 2021/4/29
     * @param: id
     * @return: int
     **/
    @RequestMapping("/deClient/{clientId}")
    public @ResponseBody int deClient(@PathVariable("clientId") int id){
        if (id > 0 ){
            return service.deleteByPrimaryKey(id);
        }else {
            return 0;
        }
    }

    /**
     * 更新一条客户信息
     * @Date: 17:59 2021/4/29
     * @param: clientInfo
     * @return: int
     **/
    @RequestMapping("/upClient")
    public @ResponseBody int upClient(ClientInfo clientInfo){

        if (clientInfo != null){
            return service.updateByPrimaryKey(clientInfo);
        }else {
            return 0;
        }
    }

    @RequestMapping("/getSelectInfo")
    public @ResponseBody List<ClientInfo> getClientIdAndName(){
        return service.getClinetIdAndName();
    }
}
