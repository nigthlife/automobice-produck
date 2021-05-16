package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.Maintain;
import com.wlp.service.MaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author 武乐萍
 * @ClassName MaintainController
 * @description
 * @date 2021-04-29 19:34
 * @Version 1.0
 */
@Controller
@CrossOrigin
@RequestMapping("/maintain")
public class MaintainController {

    @Autowired
    private MaintainService maintain;

    /**
     * 获取工单表中已处理信息
     * @return
     */
    @RequestMapping("/getMain")
    public @ResponseBody Map<String, Object> getMaintainAll(@Nullable @RequestParam("limit") Integer pageSize,
                                                            @Nullable @RequestParam("page") Integer pageIndex) {

        PageInfo<Maintain> mainAll = maintain.getFinish(pageIndex,pageSize);

        Map<String, Object> resultMap = new HashMap<>();

        for (Maintain maintain1 : mainAll.getList()) {
            System.out.println(maintain1);
        }

        resultMap.put("items",mainAll.getList());
        resultMap.put("total",mainAll.getTotal());

        return resultMap;
    }

    /**
     * 获取工单表中需处理信息
     * @return
     */
    @RequestMapping("/getMainAll")
    public @ResponseBody Map<String, Object> getMainAll(@Nullable @RequestParam("limit") Integer pageSize,
                                                        @Nullable @RequestParam("page") Integer pageIndex) {
        PageInfo<Maintain> mainAll = maintain.getMainAll(pageIndex,pageSize);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("items",mainAll.getList());
        resultMap.put("total",mainAll.getTotal());

        return resultMap;
    }

    /**
     * 更新工单信息
     * @Date: 14:18 2021/5/5
     * @param: main
     * @return: int
     **/
    @RequestMapping("/upMainData")
    public @ResponseBody Integer upMaintain(Maintain main){
        if (main != null) {
            return maintain.upMaintain(main);
        }else {
            return 0;
        }
    }

    /**
     * 新增工单
     * @Date: 8:47 2021/5/5
     * @param: main
     * @return: int
     **/
    @RequestMapping("/addMain")
    public @ResponseBody int addMaintain(Maintain main){
        if (maintain != null) {
            if (maintain.addMaintain(main) > 0){
                int max = maintain.getMax();

                Map<String,Object> map = new HashMap<>();
                map.put("Id",max);
                map.put("maintainNo","M00"+max);

                maintain.upMaintainNo(map);

                return max;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    /**
     * 获取未指派员工工单
     * @Date: 20:23 2021/4/26
     * @param: usernaem
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getNoAcc")
    public @ResponseBody List<Maintain> getNotAccendant(){

       return maintain.getAccendant();
    }

    /**
     * 查询已完成的工单
     * @Date: 21:04 2021/4/26
     * @param:
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping("/getFinish")
    public @ResponseBody
    Map<String, Object> getFinish(Integer pageIndex, Integer pageSize){

        Map<String, Object> resultMap = new HashMap<>();

        PageInfo<Maintain> finish = maintain.getFinish(pageIndex,pageSize);

        resultMap.put("total",finish.getTotal());
        resultMap.put("items",finish.getList());

        return resultMap;
    }

    /**
     * 派遣维修人员
     * @Date: 19:41 2021/4/29
     * @param: main
     * @return: java.lang.String
     **/
    @RequestMapping("/upAcc")
    public @ResponseBody int upAccendant(Maintain main) {
        if (maintain != null) {
            return maintain.dispatchAccendant(main);
        }else {
            return 0;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
