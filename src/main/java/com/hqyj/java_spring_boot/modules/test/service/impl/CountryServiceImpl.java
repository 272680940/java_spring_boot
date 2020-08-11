package com.hqyj.java_spring_boot.modules.test.service.impl;

import com.hqyj.java_spring_boot.modules.test.dao.CountryDao;
import com.hqyj.java_spring_boot.modules.test.entity.Country;
import com.hqyj.java_spring_boot.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    //通过countryId查询国家
    @Override
    public Country getCountryByCountryId(int countryId) {
        return countryDao.getCountryByCountryId(countryId);
    }

    //通过countryName查询国家
    @Override
    public Country getCountryByCountryName(String countryName) {
        return countryDao.getCountryByCountryName(countryName);
    }
}
