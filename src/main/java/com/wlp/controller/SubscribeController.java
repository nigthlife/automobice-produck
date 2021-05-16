package com.wlp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.Subscribe;
import com.wlp.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName SubscribeController
 * @description
 * @date 2021-04-27 18:05
 * @Version 1.0
 */
@Controller
@RequestMapping("/Subscribe")
@CrossOrigin
public class SubscribeController {

    @Autowired
    private SubscribeService Subscribe;

    @Autowired
    ObjectMapper mapper;

    /**
     * 获取预约表中所有信息
     * @Date: 21:28 2021/5/2
     * @param: pageIndex
     * @param: pageSize
     * @return: Map<String,Object>
     **/
    @RequestMapping(value = "/getAll",method = RequestMethod.GET )
    public @ResponseBody Map<String, Object> getSubscribeAll(@Nullable @RequestParam("page") Integer pageIndex,
                                                             @Nullable @RequestParam("limit") Integer pageSize){

        PageInfo<com.wlp.bean.Subscribe> pageInfo = Subscribe.getSubscribeAll(pageIndex,pageSize);
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("code",0);
        resultMap.put("msg","");
        resultMap.put("total",pageInfo.getTotal());
        resultMap.put("items",pageInfo.getList());

        return resultMap;
    }

    /**
     * 更新预约表中一条数据
     * @param subscribe
     */
    @RequestMapping("/upSub")
    public @ResponseBody int upSubscribe(Subscribe subscribe){

        int i = Subscribe.upSubscribe(subscribe);

        if (i > 0 ){
            return Subscribe.getMaxId();
        }else {
            return 0;
        }
    }

    /**
     * 向预约表中插入条信息
     * @param subscribe
     */
    @RequestMapping("/inSub")
    public @ResponseBody int InSubscribe(Subscribe subscribe){

        if (subscribe != null){
            if (Subscribe.inSubscribe(subscribe) > 0){
                return Subscribe.getMaxId();
            }else {
                return  0;
            }
        }else {
            return 0;
        }
    }

    /**
     * 删除一条预约记录
     * @param subId
     */
    @RequestMapping("/deSub/{subId}")
    public @ResponseBody int DelSubscribe(@PathVariable("subId") Integer subId){

        if (subId != null){
            return Subscribe.deSubscribe(new Subscribe(subId));
        }else {
            return 0;
        }
    }
}
