package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.Accendant;
import com.wlp.service.AccendantService;
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
 * @ClassName AccendantController
 * @description
 * @date 2021-04-29 14:13
 * @Version 1.0
 */
@CrossOrigin
@Controller
@RequestMapping("/accendant")
public class AccendantController {

    @Autowired
    private AccendantService accendant;

    /**
     * 分页查询维修人员表中所有数据
     * @Date: 14:21 2021/4/29
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getAccAll")
    public @ResponseBody Map<String, Object> getAccendAll(@Nullable @RequestParam("page") Integer pageIndex,
                                                          @Nullable @RequestParam("limit") Integer pageSize){

        PageInfo<Accendant> pageInfo = accendant.selectAll(pageIndex, pageSize);

        Map<String, Object> map = new HashMap<>();

        map.put("items",pageInfo.getList());
        map.put("total",pageInfo.getTotal());

        return map;
    }

    /**
     * 向维修人员表中插入一条信息
     * @Date: 15:13 2021/4/29
     * @param: accend
     * @return: int
     **/
    @RequestMapping("/addAcc")
    public @ResponseBody int addAccendant(Accendant accend){

        int insert = accendant.insert(accend);

        if (insert > 0 ){
            return accendant.getAccMax();
        }else {
            return insert;
        }
    }

    /**
     * 删除一条维修人员信息
     * @Date: 15:16 2021/4/29
     * @param: accId
     * @return: int
     **/
    @RequestMapping("/deAcc/{accendId}")
    public @ResponseBody int deAccentant(@PathVariable("accendId") int accId){
        if (accId > 0) {
            return accendant.deleteByPrimaryKey(accId);
        }else {
            return 0;
        }
    }

    /**
     * 更新维修人员信息
     * @Date: 15:18 2021/4/29
     * @param: accend
     * @return: int
     **/
    @RequestMapping("/upAcc")
    public @ResponseBody int upAccendant(Accendant accend) {
        if (accend != null){
            return accendant.updateByPrimaryKey(accend);
        }else {
            return 0;
        }
    }
}
