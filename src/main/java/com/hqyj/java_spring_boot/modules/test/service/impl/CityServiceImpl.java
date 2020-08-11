package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.modules.test.dao.CityDao;
import com.hqyj.java_spring_boot.modules.test.entity.City;
import com.hqyj.java_spring_boot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
