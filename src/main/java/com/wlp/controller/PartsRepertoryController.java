package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import com.wlp.bean.partsRepertory;
import com.wlp.service.PartsRepertoryService;
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
 * @ClassName PartsRepertoryController
 * @description
 * @date 2021-05-03 14:25
 * @Version 1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/repertory")
public class PartsRepertoryController {

    @Autowired
    private PartsRepertoryService repertoryService;

    /**
     * 分页获取仓库表信息
     * @Date: 14:27 2021/5/3
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/obtainAll")
    public @ResponseBody
    Map<String,Object> getRepertoryAll(@Nullable @RequestParam("page") Integer pageIndex,
                                       @Nullable @RequestParam("limit") Integer pageSize){
        Map<String,Object> map = new HashMap<>();

        PageInfo<partsRepertory> pageInfo = repertoryService.selectAll(pageIndex, pageSize);
        map.put("items",pageInfo.getList());
        map.put("total",pageInfo.getTotal());

        return map;
    }

    /**
     * 获取仓库中已有零件信息
     * @Date: 13:18 2021/5/5
     * @param:
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/obtainPartsAll")
    public @ResponseBody
    Map<String,Object> getPartsAll(){
        Map<String,Object> map = new HashMap<>();

        List<PartsInfo> partsInfos = repertoryService.selectPartsAll();
        map.put("items",partsInfos);
        return map;
    }

    /**
     * 向仓库表中更新零件信息
     * @Date: 18:34 2021/5/3
     * @param: repertory
     * @return: int
     **/
    @RequestMapping("/purchase")
    public @ResponseBody int purchaseParts(partsRepertory repertory){

        if (repertory != null) {
            if (repertoryService.selectPartsExist(repertory.getPartsId()) != null) {
                return repertoryService.upCount(repertory);
            }else {
                return repertoryService.inPartsInfo(repertory);
            }
        }
        return 0;
    }

    /**
     * 更新销售价格
     * @Date: 20:34 2021/5/3
     * @param: id
     * @param: price
     * @return: int
     **/
    @RequestMapping("/upPrice/{partsId}/{partsPrice}")
    public @ResponseBody int upPartsPrice(@PathVariable("partsId") Integer id,
                                          @PathVariable("partsPrice") Integer price) {
        if (id != null && price != null) {
            return repertoryService.uppartsPrice(id,price);
        }else {
            return 0;
        }

    }

}
