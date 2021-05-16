package com.wlp.controller;

import com.github.pagehelper.PageInfo;
import com.wlp.bean.CarInfo;
import com.wlp.service.CarInfoService;
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
 * @ClassName CarInfoController
 * @description
 * @date 2021-04-29 18:26
 * @Version 1.0
 */
@Controller
@RequestMapping("/carInfo")
@CrossOrigin
public class CarInfoController {

    @Autowired
    private CarInfoService service;

    /**
     * 分页获取汽车表中所有信息
     *
     * @Date: 18:41 2021/4/29
     * @param: pageIndex
     * @param: pageSize
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     **/
    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCarInfoAll(@Nullable @RequestParam("page") Integer pageIndex,
                                      @Nullable @RequestParam("limit") Integer pageSize) {

        Map<String, Object> map = new HashMap<>();

        PageInfo<CarInfo> carInfoAll = service.getCarInfoAll(pageIndex, pageSize);

        map.put("items", carInfoAll.getList());
        map.put("total", carInfoAll.getTotal());

        return map;
    }

    /**
     * 根据汽车id获取指定汽车信息
     *
     * @Date: 18:45 2021/4/29
     * @param: car
     * @return: com.wlp.bean.CarInfo
     **/
    @RequestMapping(value = "/getCar/{carId}", method = RequestMethod.POST)
    public @ResponseBody
    CarInfo getCarInfo(@PathVariable("carId") String car) {
        if (car != null) {
            return service.getCarInfo(car);

        } else {
            return null;
        }
    }

    /**
     * 新增一条信息
     *
     * @Date: 18:46 2021/4/29
     * @param: carInfo
     * @return: int
     **/
    @RequestMapping(value = "/setData", method = RequestMethod.POST)
    public @ResponseBody
    int setCarInfo(CarInfo carInfo) {
        if (carInfo != null) {
           if (service.insertCar(carInfo) > 0){
                return service.getMax();
           }else{
               return 0;
           }
        } else {
            return 0;
        }

    }

    /**
     * 删除一条汽车信息
     *
     * @Date: 18:47 2021/4/29
     * @param: carId
     * @return: int
     **/
    @RequestMapping("/delCar/{carId}")
    public @ResponseBody
    int deleteCar(@PathVariable("carId") String carId) {

        if (carId != null) {
            return service.deleteCar(carId);
        } else {
            return 0;
        }
    }

    /**
     * 更新汽车信息
     *
     * @Date: 18:48 2021/4/29
     * @param: carInfo
     * @return: int
     **/
    @RequestMapping("/upCar")
    public @ResponseBody
    int updateCar(CarInfo carInfo) {
        if (carInfo != null) {
            return service.updateCar(carInfo);
        } else {
            return 0;
        }
    }

    /**
     * 获取当前用户下的所有汽车
     * @Date: 23:01 2021/5/4
     * @param: id
     * @return: java.util.List<com.wlp.bean.CarInfo>
     **/
    @RequestMapping("/getClientCar/{clientId}")
    public @ResponseBody
    List<CarInfo> getClientCar(@PathVariable("clientId") Integer id){
        if (id != null) {
            return service.getclientCar(id);
        }else {
            return null;
        }
    }
}
