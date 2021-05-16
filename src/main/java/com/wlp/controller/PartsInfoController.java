package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.PartsInfo;
import com.wlp.service.PartsInfoService;
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
 * @ClassName PartsInfoController
 * @description
 * @date 2021-04-30 14:56
 * @Version 1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/partsInfo")
public class PartsInfoController {

    @Autowired
    private PartsInfoService parts;

    /**
     * 分页查询所以零件
     * @Date: 15:03 2021/4/30
     * @param: pageIndex 页码
     * @param: pageSize 页大小
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getParts")
    public @ResponseBody
    Map<String,Object> getPartsInfo(@Nullable @RequestParam("page") Integer pageIndex,
                                     @Nullable @RequestParam("limit") Integer pageSize){
        Map<String,Object> map = new HashMap<>();

        PageInfo<PartsInfo> partsInfos = parts.selectAll(pageIndex, pageSize);
        map.put("items",partsInfos.getList());
        map.put("total",partsInfos.getTotal());

        return map;
    }

    /**
     * 新增零件
     * @Date: 15:03 2021/4/30
     * @param: partsInfo 零件类实体
     * @return: int
     **/
    @RequestMapping("/addParts")
    public @ResponseBody int addParts(PartsInfo partsInfo){

        if (partsInfo != null) {
            if (parts.inPartsInfo(partsInfo) > 0){
                return parts.getMax();
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    /**
     * 删除零件
     * @Date: 15:03 2021/4/30
     * @param: id 零件id
     * @return: int
     **/
    @RequestMapping("/deClient/{partsId}")
    public @ResponseBody int dePartsId(@PathVariable("partsId") int id){
        if (id > 0 ){
            return parts.dePartsInfo(id);
        }else {
            return 0;
        }
    }

    /**
     * 更新零件信息
     * @Date: 15:06 2021/4/30
     * @param: partsInfo
     * @return: int
     **/
    @RequestMapping("/upParts")
    public @ResponseBody int upParts(PartsInfo partsInfo){

        if (partsInfo != null){
            return parts.upPartsInfo(partsInfo);
        }else {
            return 0;
        }
    }

}
