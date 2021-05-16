package com.wlp.dao;

import com.wlp.bean.CarInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarInfoDao {
    /**
     * 获取汽车表中所有信息
     *
     * @return
     */
    List<CarInfo> getCarInfoAll();

    /**
     * 根据汽车id回去该汽车信息
     *
     * @param userid
     * @return
     */
    CarInfo getCarInfo(String userid);

    /**
     * 插入用户信息
     *
     * @param carInfo
     * @return
     */
    int insertCar(CarInfo carInfo);

    /**
     * 删除一个汽车信息
     *
     * @param carId
     * @return
     */
    int deleteCar(String carId);

    /**
     * 更新汽车信息
     *
     * @param carInfo
     * @return
     */
    int updateCar(CarInfo carInfo);

    /**
     * 获取最新插入的汽车编号
     * @Date: 22:33 2021/5/2
     * @param:
     * @return: int
     **/
    int getMax();

    /**
     * 获取当前用户下的所有汽车
     * @Date: 22:55 2021/5/4
     * @param: client
     * @return: java.util.List<com.wlp.bean.CarInfo>
     **/
    List<CarInfo> getclientCar(@Param("clientId") Integer client);
}
