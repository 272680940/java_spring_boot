package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.dao.CityDao;
import com.hqyj.java_spring_boot.modules.test.entity.City;
import com.hqyj.java_spring_boot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    //通过countryId 查询所有的城市
    @Override
    public List<City> getCitiesByCountryId(int countryId) {
//        return cityDao.getCitiesByCountryId(countryId);//原来的写法
        //新的写法
        return Optional
                .ofNullable(cityDao.getCitiesByCountryId(countryId))//若查询结果不为空，则返回集合
                .orElse(Collections.emptyList());//查询结果为空，则返回空集
    }

    //分页查询所有的城市
    @Override
    public PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo) {
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<City>(
                Optional.ofNullable(cityDao.getCitiesByCountryId(countryId))//若查询结果不为空，则返回集合
                .orElse(Collections.emptyList())//查询结果为空，则返回空集
        );
    }

    //脚本式多条件查询
    @Override
    public PageInfo<City> getCitiesBySearchVo(SearchVo searchVo) {
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<City>(
                Optional.ofNullable(cityDao.getCitiesBySearchVo(searchVo))//若查询结果不为空，则返回集合
                        .orElse(Collections.emptyList())//查询结果为空，则返回空集
        );
    }


    /**
     * 添加城市信息
     * 返回状态值、信息和 city 对象
     */
    @Override
    @Transactional//事务
    public Result<City> insertCity(City city) {
        cityDao.insertCity(city);//执行查询语句
        return new Result<City>(Result.ResultStatus.SUCCESS.status
                ,"insert success!",city);
    }

    /**
     * 修改城市信息
     * 返回状态值和信息
     */
    @Override
    @Transactional//事务
    public Result<City> updateCity(City city) {
        cityDao.updateCity(city);
        return new Result<City>(Result.ResultStatus.SUCCESS.status
                ,"update success!");
    }

    /**
     * 删除城市信息
     * 返回状态值和信息
     */
    @Override
    @Transactional//事务
    public Result<City> deleteCity(int cityId) {
        cityDao.deleteCity(cityId);
        return new Result<City>(Result.ResultStatus.SUCCESS.status
                ,"delete success!");
    }
}
