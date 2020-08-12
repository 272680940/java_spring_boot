package com.hqyj.java_spring_boot.modules.test.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.entity.City;

import java.util.List;

public interface CityService {

    //通过countryId 查询所有的城市
    List<City> getCitiesByCountryId(int countryId);

    //分页查询所有城市
    PageInfo<City> getCitiesBySearchVo(int countryId,SearchVo searchVo);

    //脚本式多条件查询
    PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);

    //添加
    Result<City> insertCity(City city);

    //修改
    Result<City> updateCity(City city);

    //删除
    Result<City> deleteCity(int cityId);
}
