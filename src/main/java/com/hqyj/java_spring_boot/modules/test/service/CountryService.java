package com.hqyj.java_spring_boot.modules.test.service;

import com.hqyj.java_spring_boot.modules.test.entity.Country;

public interface CountryService {
    //通过countryId查询国家
    Country getCountryByCountryId(int countryId);

    //通过countryName查询国家
    Country getCountryByCountryName(String countryName);

    //Redis
    Country mograteCountryByRedis(int countryId);
}
