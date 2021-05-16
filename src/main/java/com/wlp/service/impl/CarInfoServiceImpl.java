package com.wlp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlp.bean.CarInfo;
import com.wlp.dao.CarInfoDao;
import com.wlp.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoDao carInfoMapper;

    @Override
    public PageInfo<CarInfo> getCarInfoAll(Integer pageIndex, Integer pageSize) {

        if (pageIndex != null && pageSize != null){
            PageHelper.startPage(pageIndex,pageSize);
        }
        return new PageInfo<>(carInfoMapper.getCarInfoAll());
    }

    @Override
    public CarInfo getCarInfo(String userid) {
        return carInfoMapper.getCarInfo(userid);
    }

    @Override
    public int insertCar(CarInfo carInfo) {
        return carInfoMapper.insertCar(carInfo);
    }

    @Override
    public int deleteCar(String carId) {
        return carInfoMapper.deleteCar(carId);
    }

    @Override
    public int updateCar(CarInfo carInfo) {
        return carInfoMapper.updateCar(carInfo);
    }

    @Override
    public int getMax() {
        return carInfoMapper.getMax();
    }

    @Override
    public List<CarInfo> getclientCar(Integer client) {
        return carInfoMapper.getclientCar(client);
    }
}
